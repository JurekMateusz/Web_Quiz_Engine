# Web Quiz Engine - JetBrains Acadamy

* Get the quiz (stage 1/6 -- 5/6)

The quiz has exactly three fields: title (string) text (string) and options (array).
To get the quiz, the client sends the GET request to /api/quiz.
The server should return the following JSON structure:

```http request
http://localhost:8889/api/quizzes/
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

```http request
http://localhost:8889/api/quizzes/{id}
```
```json
{
  "id": 1,
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
}
```

* Solve the quiz

To solve a quiz, the client sends the POST request to 
\/api/quizzes/{id}/solve with a JSON that contains the indexes of all
chosen options as the answer. This looks like a regular JSON object with key
"answer" and value as the array: {"answer": [0,2]}. As before, indexes start from zero.

It is also possible to send an empty array [] since some quizzes may not have correct options.
If the passed answer is correct (POST to /api/quiz with content answer=2):

```http request
http://localhost:8889/api/quizzes/2/solve
```
Post body(example)
```json
{"answer": [1,2]}
```
response:
```json
{"success":true,"feedback":"Congratulations, you're right!"}
```
If the answer is incorrect (e.g., POST to /api/quiz with content answer=1):
```http request 
http://localhost:8889/api/quizzes/1/solve
```
response:
```json
{"success":false,"feedback":"Wrong answer! Please, try again."}
```

* Create a new quiz

To create a new quiz, the client needs to send a JSON as the request's body via 
POST to /api/quizzes. The JSON should contain the four fields:
```
title: a string, required;
text: a string, required;
options: an array of strings, required, should contain at least 2 items;
answer: an array of indexes of correct options, optional, since all options can be wrong.
Here is a new JSON quiz as an example:
```
```json
{
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"],
  "answer": [0,2]
}
```

The answer equals 2 corresponds to the third item from the options array ("Cup of coffee").

The server response is a JSON with four fields: id, title, text and options. Here is an example.
```json
{
  "id": 1,
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"]
}
```

* Get all quizzes with paging (MODIFIED) - STAGE 6/6
To get all existing quizzes in the service, the client sends the GET request to /api/quizzes as before. But here is the problem: 
the number of stored quizzes can be very large since your service is so popular among so many users.

In this regard, your API should return only 10 quizzes at once and supports the ability to specify which portion of quizzes is needed.
```http request
http://localhost:8889/api/quizzes/
```
```json
{
  "totalPages":1,
  "totalElements":3,
  "last":true,
  "first":true,
  "sort":{ },
  "number":0,
  "numberOfElements":3,
  "size":10,
  "empty":false,
  "pageable": { },
  "content":[
    {"id":102,"title":"Test 1","text":"Text 1","options":["a","b","c"]},
    {"id":103,"title":"Test 2","text":"Text 2","options":["a", "b", "c", "d"]},
    {"id":202,"title":"The Java Logo","text":"What is depicted on the Java logo?",
     "options":["Robot","Tea leaf","Cup of coffee","Bug"]}
  ]
}
```
* Get all completions of quizzes with paging (NEW)
```http request
http://localhost:8889/api/quizzes/completed?pageNo=0&pageSize=3
```
```json
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
    {"id":103,"completedAt":"2019-10-29T21:13:53.779542"},
    {"id":102,"completedAt":"2019-10-29T21:13:52.324993"},
    {"id":101,"completedAt":"2019-10-29T18:59:58.387267"},
    {"id":101,"completedAt":"2019-10-29T18:59:55.303268"},
    {"id":202,"completedAt":"2019-10-29T18:59:54.033801"}
  ]
}
```