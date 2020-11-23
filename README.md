# Yelp-Reviews-Dimensional-Data-Model

## About the dataset
Yelp crowd sources reviews of local businesses. This dataset is a subset of businesses, reviews, and user data across 10 metropolitan areas: Montreal, Calgary, Toronto, Pittsburgh, Charlotte, Urbana-Champaign, Phoenix, Las Vegas, Madison, and Cleveland. Only reviews that Yelp recommended at the time of data collection were included. This amounts to 8,021,122 reviews and 209,393 businesses.

## ETL Steps
![]()
Transform with Spark, send to DW, model with Visual Studio

## Dimensional model
![]()
- Identify the business process: Ad Sales
- Identify the grain: Individual customer review
- Choose the dimensions
    - Who: User
    - What: Category
    - When: Checkin
    - Where: Business
- Choose the measure(s): Review stars

## Use case
Help drive traffic to Business Page with Ads 
“Yelp Ads puts your business in front of consumers nearby who are looking to make a purchase. 82% of Yelp users visit intending to buy a product or service and 89% of those who buy do so within a week, according to Nielsen.”
Create queries to find businesses with the lowest review counts in their category.
