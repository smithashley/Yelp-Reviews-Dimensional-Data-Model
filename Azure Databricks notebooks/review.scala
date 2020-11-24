// Databricks notebook source
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

// COMMAND ----------

//Configure access to Blob Storage
spark.conf.set(
  "fs.azure.account.key.yelp1.blob.core.windows.net",
  "storage_account_access_key")

// COMMAND ----------

//Read from Azure Blob Storage
val df = spark.read.json("wasbs://rawdata@yelp1.blob.core.windows.net/yelp_academic_dataset_review.json")

// COMMAND ----------

val dateDF = df.withColumn("date", split(col("date"), " ").getItem(0))

// COMMAND ----------

//Select needed columns
val selectDF = df.select("review_id", "user_id", "business_id", "stars", "date", "useful", "funny", "cool")

// COMMAND ----------

selectDF.printSchema()

// COMMAND ----------

//Fix data types
val reviewDF = selectDF.withColumn("stars", (col("stars").cast(IntegerType)))
  .withColumn("date", (col("date").cast(DateType)))
  .withColumn("useful", (col("useful").cast(IntegerType)))
  .withColumn("funny", (col("funny").cast(IntegerType)))
  .withColumn("cool", (col("cool").cast(IntegerType)))

// COMMAND ----------

reviewDF.printSchema()

// COMMAND ----------

//Create date dimension table
val df2 = reviewDF.select("date")

// COMMAND ----------

//windowing functions
val windowDF = df2.withColumn("date_id", (regexp_replace((col("date")), "-", "")))
  .withColumn("calendar_quarter", quarter(col("date")))
  .withColumn("calendar_year", year(col("date")))
  .withColumnRenamed("date", "alt_date_id")

// COMMAND ----------

val dateDF = windowDF.select("date_id", "alt_date_id", "calendar_quarter", "calendar_year")

// COMMAND ----------

dateDF.printSchema()

// COMMAND ----------

spark.conf.set("fs.azure.account.key.dwpool.blob.core.windows.net", "storage_account_access_key")

// COMMAND ----------

reviewDF.write
  .format("com.databricks.spark.sqldw")
  .option("url", "jdbc:sqlserver://afssqldw.database.windows.net:1433;database=afssqldw2;user=afsmithcodes@afssqldw;password={password_redacted};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;")
  .option("forwardSparkAzureStorageCredentials", "true")
  .option("dbTable", "review")
  .option("tempDir", "wasbs://yelpdw@dwpool.blob.core.windows.net/tempDirs")
  .save()

// COMMAND ----------

dateDF.write
  .format("com.databricks.spark.sqldw")
  .option("url", "jdbc:sqlserver://afssqldw.database.windows.net:1433;database=afssqldw2;user=afsmithcodes@afssqldw;password={password_redacted};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;")
  .option("forwardSparkAzureStorageCredentials", "true")
  .option("dbTable", "date")
  .option("tempDir", "wasbs://yelpdw@dwpool.blob.core.windows.net/tempDirs")
  .save()

// COMMAND ----------


