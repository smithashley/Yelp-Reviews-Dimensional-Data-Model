# Yelp-Reviews-Dimensional-Data-Model

## About the dataset
- This dataset is a subset of businesses, reviews, and user data across 10 metropolitan areas: Montreal, Calgary, Toronto, Pittsburgh, Charlotte, Urbana-Champaign, Phoenix, Las Vegas, Madison, and Cleveland. Only reviews that Yelp recommended at the time of data collection were included. 
- This amounts to 8,021,122 reviews of 209,393 businesses (10 GB of data)
- Dataset is available here: https://www.yelp.com/dataset
- Example of the format
![](https://github.com/smithashley/Yelp-Reviews-Dimensional-Data-Model/blob/main/images/exjson.png)

## Objective
![](https://github.com/smithashley/Yelp-Reviews-Dimensional-Data-Model/blob/main/images/yd_diagram.png)

- Extract data, transform the JSON files to parquet files, and load to data warehouse
    - Load Yelp dataset to Azure Blob Storage
    - Configure connection to Azure Blob Storage from Azure Databricks
    - Read data from staging area
    - Clean data, creating schema for new tabular format
    - Configure connection to Azure Synapse Analytics 
    - Write data to Azure Synapse Analytics
- Create dimensional data model to show entity relationships

## Use case
- “Yelp Ads puts your business in front of consumers nearby who are looking to make a purchase. 82% of Yelp users visit intending to buy a product or service and 89% of those who buy do so within a week, according to Nielsen.”
- Yelp crowd sources reviews of local businesses
- Use Ads to help drive traffic to businesses with a lower review count

## Dimensional model
- Identify the business process: Ad Sales
- Identify the grain: Individual customer review
- Choose the dimensions
    - Who: User
    - What: Category
    - When: Date
    - Where: Business
- Choose the measure(s): Review stars

![](https://github.com/smithashley/Yelp-Reviews-Dimensional-Data-Model/blob/main/images/dim_data_model.png)
