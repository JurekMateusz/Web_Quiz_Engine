# Web Quiz Engine - JetBrains Acadamy

To run this project ,you need to clone Entity/dto Mapper from: 
```http request
https://github.com/Leonzio20/assemblers
```
build and change dependency in web_quiz_engine/pom.xml to your local system path.

<h2><strong> Register </strong></h2>


```http request
POST http://localhost:8889/api/auth/register
``` 
```json
{
  "email": "test@gmail.com",
  "password": "testt"
}
```
Response 
```http request
http status: CREATED(201) 
```


 <h2><strong> Login </strong> </h2>

```http request
POST http://localhost:8889/api/auth/login
``` 
with the same request body as in register.

Response:

```json
{
    "authenticationToken": "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTU5ODE4ODM2OCwiZXhwIjoxNTk4MTg4MzY4fQ.V03TGB2x1LYRWbPN11bVkaI24j8-2UFvh4sjpCTWikMqJhDapdTkkYcnEnySaDfjYBMAeQUFtzC3KG7IuCoviUQUhdsNSThSc4c-8byY_0yOUWF67r3Zv0BkMRaiOE0g94YEm9U70H0I00SgOOoN-JOfJpx2s6W5wtFpB5QwezuASWlG6Yr4NbSn-uaWGuHwRRDPDschPQmmavhk34ArXIyajFjy1af0iYrlg17imsNL9t63CsOgx9Nt3WyECvdlBBt8yAvF4nYoaAuSZNG68cOCyHhswCOyX9v92MD5XKztxg9Vgxo_awsmwdryhK4TV4GrOB8NVluknr29hgK3jw",
    "refreshToken": "df75a1f2-df7c-4a18-b778-9cab2ca0add5",
    "expiresAt": "2020-08-23T13:12:48.388161800Z",
    "username": "test@gmail.com"
}
```

<h2><strong> Refresh token  </strong></h2>

```http request
POST https://localhost:8889/api/auth/token/refresh
```
with body:
```json
{
    "refreshToken": "df75a1f2-df7c-4a18-b778-9cab2ca0add5",
    "email": "test@gmail.com"
}
```

Response:
```json
{
    "authenticationToken": "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTU5ODE4ODQ2NiwiZXhwIjoxNTk4MTg4NDY3fQ.xiW5kGIC2DEC_3tY5i7w1cnBv_tcQIDAgZB52nJ0Cd4VwiGL42XqKcSAKTgf2Cy2ab5TKaTCLghStAcfAS3nCJtA6OBkwWgpiwTPFtiqP1njh6YPUafAYFhBdDptjli3u3AhOnNSAW41H7K0mlvc_nNrazkJJt4T_HQkElsGayiiQ6W0Yi8t4HPh_AQ8zyqSxZiwSmXDggCA2mkIxx7nG5K-5wT1vhL_oCuTDmz6tBcAU6unVaQ_Dzf0AZfR8oMFyzibbNgTl7L3JQDig-nDLj3LaAdXDAQ8qkQ6hoGRl5y5fOS9UJv-sjqJ5FzrON2YuA2LYK2mnd84K2N9KgzyhA",
    "refreshToken": "df75a1f2-df7c-4a18-b778-9cab2ca0add5",
    "expiresAt": "2020-08-23T13:14:27.076232400Z",
    "username": "test@gmail.com"
}
```
 <h2><strong>Logout</strong></h2>
 
 ```http request
POST https://localhost:8889/api/auth/logout
```
body:
```json
{
    "refreshToken": "df75a1f2-df7c-4a18-b778-9cab2ca0add5",
    "username": "test@gmail.com"
}
``` 
 

<h2><strong> Add new quiz </strong></h2>

```http request
POST http://localhost:8889/api/quizzes/
```
with example body:
```json
{
   "title":"Title test",
   "quizQuestions":[
      {
         "question":"test test ttest   ?",
         "quizAnswerQuestions":[
            {
               "answer":"test ???",
               "correct":false
            },
            {
               "answer":"test !!!",
               "correct":true
            }
         ]
      }
   ]
}
```
Response:
```http request
http status: CREATED(201) 
```


<h2><strong> Get the quizzes </strong></h2>

```http request
GET http://localhost:8889/api/quizzes/
```
```json
[
  {
    "id": 1,
    "title": "The Java Logo",
    "text": "What is depicted on the Java logo?",
    "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
  },
  {
    "id": 2,
    "title": "The Ultimate Question",
    "text": "What is the answer to the Ultimate Question of Life, the Universe and Everything?",
    "options": ["Everything goes right","42","2+2=4","11011100"]
  }
]
```

<h2><strong>Delete quiz</strong></h2>

```http request
DELETE https://localhost:8889/api/quizzes/{id}
```
