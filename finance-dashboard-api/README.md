# Finance Dashboard Backend

## Overview

Backend system for managing financial records with role-based access control.

## Tech Stack

* Java
* Spring Boot
* MySQL

## Features

* User & Role Management
* Financial Records CRUD
* Filtering (date, category, type)
* Dashboard Summary APIs
* Role-Based Access Control
* Validation & Error Handling

## Architecture

Controller → Service → Repository → Database

## API Endpoints

* POST /users
* GET /users/{id}
* POST /records
* GET /records
* GET /dashboard/summary

## Running the Project

1. Clone:
   git clone <your-repo-url>

2. Configure DB in:
   application.properties

3. Run:
   mvn spring-boot:run

## Author

Amit Chouhan
