# article-entity-scrapper

This application has two endpoints:
1. curl --location 'http://localhost:8080/api/get-entities' \
--header 'Content-Type: application/json' \
--data '{
    "url": "https://www.theguardian.com/sport/live/2023/sep/15/england-v-new-zealand-fourth-mens-one-day-cricket-international-live"}'

2. curl --location 'http://localhost:8080/api/get-entities-relation' \
--header 'Content-Type: application/json' \
--data '{
    "url": "https://www.theguardian.com/sport/live/2023/sep/15/england-v-new-zealand-fourth-mens-one-day-cricket-international-live"}'

PS: 2nd API reqquest can take  1-2 mins depending upon length of article. Because a ML model is being running in the backend.
