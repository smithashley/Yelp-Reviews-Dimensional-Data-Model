// Databricks notebook source
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.FloatType
import org.apache.spark.sql.types.DateType

// COMMAND ----------

//Configure access to Blob Storage
spark.conf.set(
  "fs.azure.account.key.yelp1.blob.core.windows.net",
  "storage_account_access_key")

// COMMAND ----------

//Read from Azure Blob Storage
val df = spark.read.json("wasbs://rawdata@yelp1.blob.core.windows.net/yelp_academic_dataset_user.json")

// COMMAND ----------

//Select needed columns
val selectDF = df.select("user_id", "name", "review_count", "average_stars", "yelping_since", "useful", "funny", "cool")

// COMMAND ----------

selectDF.printSchema()

// COMMAND ----------

//Fix data types
val userDF = selectDF.withColumn("review_count", (col("review_count").cast(IntegerType)))
  .withColumn("average_stars", (col("average_stars").cast(FloatType)))
  .withColumn("yelping_since", (col("yelping_since").cast(DateType)))
  .withColumn("useful", (col("useful").cast(IntegerType)))
  .withColumn("funny", (col("funny").cast(IntegerType)))
  .withColumn("cool", (col("cool").cast(IntegerType)))

// COMMAND ----------

userDF.printSchema()

// COMMAND ----------

spark.conf.set("fs.azure.account.key.dwpool.blob.core.windows.net", "storage_account_access_key")

// COMMAND ----------

userDF.write
  .format("com.databricks.spark.sqldw")
  .option("url", "jdbc:sqlserver://afssqldw.database.windows.net:1433;database=afssqldw2;user=afsmithcodes@afssqldw;password={password_redacted};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;")
  .option("forwardSparkAzureStorageCredentials", "true")
  .option("dbTable", "user")
  .option("tempDir", "wasbs://yelpdw@dwpool.blob.core.windows.net/tempDirs")
  .save()
