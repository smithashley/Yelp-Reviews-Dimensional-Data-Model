// Databricks notebook source
import org.apache.spark.sql.functions.{col, split}
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.FloatType

// COMMAND ----------

//Configure access to Blob Storage
spark.conf.set(
  "fs.azure.account.key.yelp1.blob.core.windows.net",
  "storage_account_access_key")

// COMMAND ----------

//Read from Azure Blob Storage
val df = spark.read.json("wasbs://rawdata@yelp1.blob.core.windows.net/yelp_academic_dataset_business.json")

// COMMAND ----------

df.show(5)

// COMMAND ----------

//Select needed columns
val selectDF = df.select("business_id", "name", "address", "city", "state", "stars", "review_count")

// COMMAND ----------

selectDF.printSchema()

// COMMAND ----------

//Fix data types
val businessDF = selectDF.withColumn("stars", (col("stars").cast(FloatType)))
  .withColumn("review_count", (col("review_count").cast(IntegerType)))

// COMMAND ----------

businessDF.printSchema()

// COMMAND ----------

//Create dataframe for Categories dimension
val cDF = df.select("business_id", "categories")

// COMMAND ----------

//Since there can only be only value pero column, split categories
val categoriesDF = cDF.withColumn("category_1", split(col("categories"),",").getItem(0))
  .withColumn("category_2", split(col("categories"),",").getItem(1))
  .withColumn("category_3", split(col("categories"),",").getItem(2))
  .drop("categories")

// COMMAND ----------

categoriesDF.printSchema()

// COMMAND ----------

spark.conf.set("fs.azure.account.key.dwpool.blob.core.windows.net", "storage_account_access_key")

// COMMAND ----------

businessDF.write
  .format("com.databricks.spark.sqldw")
  .option("url", "jdbc:sqlserver://afssqldw.database.windows.net:1433;database=afssqldw2;user=afsmithcodes@afssqldw;password={password_redacted};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;")
  .option("forwardSparkAzureStorageCredentials", "true")
  .option("dbTable", "business")
  .option("tempDir", "wasbs://yelpdw@dwpool.blob.core.windows.net/tempDirs")
  .save()

// COMMAND ----------

categoriesDF.write
  .format("com.databricks.spark.sqldw")
  .option("url", "jdbc:sqlserver://afssqldw.database.windows.net:1433;database=afssqldw2;user=afsmithcodes@afssqldw;password={password_redacted};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;")
  .option("forwardSparkAzureStorageCredentials", "true")
  .option("dbTable", "categories")
  .option("tempDir", "wasbs://yelpdw@dwpool.blob.core.windows.net/tempDirs")
  .save()

// COMMAND ----------


