# 🌟 Project Title: Flashcard and Quiz Website (AI Integration)

## 🍀 Description
- A website that allows users to create flashcards (questions) for studying.
- Users can create sets of flashcards, add flashcards to sets, and study sets of flashcards.
- Users can upload a document file to create a set of flashcards from the document (AI feature).
- Users can share sets of flashcards publicly or with restricted users.
- Generate a quiz from a set of questions.
- Generate resource pages from questions and answers.

## 👨‍🔬 Actors
- User
- Staff

## 🚀 Features
### Authentication Management
- User
  - Sign up by email and password: `/api/auth/signup`
  - Log in by email and password: `/api/auth/login`
  - Log out

### Admin Management
- Staff
  - Get number of users, sets, questions (by types), quizzes.
  - Get number of quiz tests and average score of all tests.

### Set Management
- User
  - Create a set
  - Upload a document file to create a set of flashcards (AI feature)
  - Update information of a set
  - Delete a set
  - Share a set with other users or publicly
  - Get a set with all questions and answers
    - Authorised user (owner or shared users)
    - Pagination query by page and pageSize (default pageSize = 50)
    - Query by keyword (by question title, answer content)
    - Filter by question types
    - Sort by created_at, title (sort direction: asc, desc), default sort by created_at asc

### Question (Flashcard) Management
- User
  - Create a question and add it to a set
    - There are 3 types of questions:
      - Text_Fill: fill in the blank
      - Multiple_Choice: choose a correct answer
      - Checkboxes: choose multiple correct answers
    - At least 1 correct answer
  - Update information of a question and all answers of that question
  - Delete a question
  - Get a question with all answers

### Quiz Management
- User
  - Generate a quiz from a set of questions
    - Input:
      - Number of questions in the quiz
      - Question types in the quiz
  - Submit a quiz
    - Authorised user (owner or shared users)
    - User can leave some questions unanswered
    - If any answer is bad request, don't save quiz result (use transaction)
  - Get all quizzes (and shared)
  - Get all quiz results
  - Get a quiz result detail
  - Do a half quiz, save and continue later
  - Share a quiz with other users or publicly
