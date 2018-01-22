- Open project from Eclipse or intellij IDE and run project.
- Send to this URL ‘http://localhost:8080/generate-plan’ Post request :
Body : 
{
"loanAmount": "5000", 
"nominalRate": "5.0",
"duration": 24,
"startDate": "2018-01-01T00:00:01Z"
}
Headers :
Content-Type : application/json