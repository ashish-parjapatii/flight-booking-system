# Flight Booking System

A concurrent flight booking service built to explore correctness under contention.

**Stack:** Java 25 · Spring Boot · PostgreSQL · Redis · Docker

**Status:** In development - domain model.

## The problem this solves

Two users clicking "book" on the last seat at the same moment must not both
succeed. Everything in this system follows from that constraint.