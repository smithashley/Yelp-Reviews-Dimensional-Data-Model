# Yelp-Reviews-Dimensional-Data-Model

## Objective
![](https://github.com/smithashley/Yelp-Reviews-Dimensional-Data-Model/blob/main/images/yd_diagram.png)

- Transform the JSON data to parquet file
    - Load Yelp dataset to Azure Blob Storage
    - Configure connection to Azure Blob Storage from Azure Databricks
    - Clean data, creating schema for new tabular format
- Load tables to Azure Synapse Analytics
- Create dimensional data model to show entity relationships

## About the dataset
- This dataset is a subset of businesses, reviews, and user data across 10 metropolitan areas: Montreal, Calgary, Toronto, Pittsburgh, Charlotte, Urbana-Champaign, Phoenix, Las Vegas, Madison, and Cleveland. Only reviews that Yelp recommended at the time of data collection were included. 
- This amounts to 8,021,122 reviews of 209,393 businesses.
- Dataset is available here: https://www.yelp.com/dataset
- Example of format for the dataset
![](https://github.com/smithashley/Yelp-Reviews-Dimensional-Data-Model/blob/main/images/jsonexample.png)

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
