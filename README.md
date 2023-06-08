# Contact-Management-Web-App
![Screenshot from 2023-06-07 03-04-18](https://github.com/tati2002med/Contact-Management-Web-App/assets/95311883/9ead2f13-e4e4-482f-b00e-8036c20c258b)

## App Description
A `Contact Management App` is a software application that allows users to organize and store contact information, such as names, phone numbers, email addresses, and other details, in a digital format.

It is a useful tool for anyone who needs to manage a large number of contacts and stay on top of their communication and networking activities.

#### Features
- `NLP`, The search is optimized using the NLP Algorithm (`Min Edit Distance`).
- `CRUD`, you can add, delete, update, restore and manage your contacts & groups.
- `LOGS`, you can track your daily logging displayed as Char using `Char.js`.
- `Multi-User`, The app support multi user feature by creating user account and manage your contacts.
- `Security`, As this is Spring Boot Application we have used `Spring Security` to manage the users.

#### App Design
- This App designed using `CSS`, `HTML`, `BootStrap` only.
- The web pages are implemented using `Thymeleaf`.

## Repository Architecture
``` bash
.
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── app
│   │   │           └── ContactManagement
│   │   │               ├── config
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── ContactManagementApplication.java
│   │   │               ├── controller
│   │   │               │   ├── Authentications.java
│   │   │               │   ├── ContactController.java
│   │   │               │   ├── GroupController.java
│   │   │               │   ├── Navigator.java
│   │   │               │   ├── Signup.java
│   │   │               │   └── TrashController.java
│   │   │               ├── model
│   │   │               │   ├── ContactGroup.java
│   │   │               │   ├── Contact.java
│   │   │               │   ├── Groupe.java
│   │   │               │   ├── LoginCounter.java
│   │   │               │   ├── Trash.java
│   │   │               │   └── User.java
│   │   │               ├── repository
│   │   │               │   ├── ContactGroupRepository.java
│   │   │               │   ├── ContactRepository.java
│   │   │               │   ├── GroupRepository.java
│   │   │               │   ├── LoginCounterRepository.java
│   │   │               │   ├── TrashRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── service
│   │   │               │   ├── ContactServiceImpl.java
│   │   │               │   ├── ContactService.java
│   │   │               │   ├── GroupServiceImpl.java
│   │   │               │   ├── GroupService.java
│   │   │               │   ├── MyUserDetails.java
│   │   │               │   ├── MyUserDetailsService.java
│   │   │               │   ├── UserServiceImpl.java
│   │   │               │   └── UserService.java
│   │   │               ├── ServletInitializer.java
│   │   │               └── utils
│   │   │                   ├── AddContactToGroup.java
│   │   │                   ├── DeleteGroup.java
│   │   │                   └── NLP.java
│   │   ├── resources
│   │   │   ├── application.properties
│   │   │   ├── static
│   │   │   │   ├── css
│   │   │   │   │   ├── addContactStyle.css
│   │   │   │   │   ├── deleteContactStyle.css
│   │   │   │   │   ├── groupsStyle.css
│   │   │   │   │   ├── homeCss.css
│   │   │   │   │   ├── searchContactStyle.css
│   │   │   │   │   ├── style.css
│   │   │   │   │   ├── styles.css
│   │   │   │   │   ├── updateContactStyle.css
│   │   │   │   │   └── welcomeStyle.css
│   │   │   │   ├── js
│   │   │   │   │   └── scripts.js
│   │   │   │   └── rsc
│   │   │   │       ├── bg-form.jpg
│   │   │   │       ├── bg.jpg
│   │   │   │       └── favicon.ico
│   │   │   └── templates
│   │   │       ├── addContactPage.html
│   │   │       ├── deleteContactPage.html
│   │   │       ├── groupsPage.html
│   │   │       ├── homePage.html
│   │   │       ├── loginPage.html
│   │   │       ├── searchContactPage.html
│   │   │       ├── signupPage.html
│   │   │       ├── updateContactPage.html
│   │   │       └── welcomePage.html
│   │   └── webapp
│   └── test
│       └── java
│           └── com
│               └── app
│                   └── ContactManagement
│                       └── ContactManagementApplicationTests.java
└── target
    ├── **

50 directories, 119 files
```

## Suggestions
- We can make the app flexible using `React` as front-end and `Spring Boot RESTful API` as backend.

## Contact Me
- Email: `mohammed.tati21@gmail.com`
- Phone: `+212682633363`
- LinkedIn: <a href="https://www.linkedin.com/in/mohammed-tati-2b3665222/">
<img src="https://camo.githubusercontent.com/fecb06c5b51c0c605a7db2b5e549d180fa3fb38e87a0d6011c3c9c830a2c68b7/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4c696e6b6564496e2d626c75653f7374796c653d666c6174266c6f676f3d4c696e6b6564696e266c6f676f436f6c6f723d7768697465" alt="Linkedin Badge" data-canonical-src="https://img.shields.io/badge/LinkedIn-blue?style=flat&logo=Linkedin&logoColor=white" style="max-width: 100%; mergin-top:15px"></a>
