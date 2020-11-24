# Yelp-Reviews-Dimensional-Data-Model

## Objective
![](https://github.com/smithashley/Yelp-Reviews-Dimensional-Data-Model/blob/main/images/yd_diagram.png)

- Transform the JSON data 
- Load tables to Azure Synapse Analytics where we can run queries 
- Create dimensional data model to show entity relationships

## About the dataset
- Yelp crowd sources reviews of local businesses. 
- This dataset is a subset of businesses, reviews, and user data across 10 metropolitan areas: Montreal, Calgary, Toronto, Pittsburgh, Charlotte, Urbana-Champaign, Phoenix, Las Vegas, Madison, and Cleveland. Only reviews that Yelp recommended at the time of data collection were included. 
- This amounts to 8,021,122 reviews of 209,393 businesses.
- Dataset is available here: https://www.yelp.com/dataset

## Use case
- Use Ads to help drive traffic to businesses with a lower review count
- “Yelp Ads puts your business in front of consumers nearby who are looking to make a purchase. 82% of Yelp users visit intending to buy a product or service and 89% of those who buy do so within a week, according to Nielsen.”

## Dimensional model
- Identify the business process: Ad Sales
- Identify the grain: Individual customer review
- Choose the dimensions
    - Who: User
    - What: Category
    - When: Checkin
    - Where: Business
- Choose the measure(s): Review stars

![](https://github.com/smithashley/Yelp-Reviews-Dimensional-Data-Model/blob/main/images/dim_model.png)
