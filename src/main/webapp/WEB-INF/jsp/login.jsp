<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chatter - Login</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
    </head>

    <body>
        <main class="container">
            <article>
                <header>
                    <h1>Login</h1>
                </header>
                <form action="/login" method="post">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>

                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    <button type="submit">Login</button>
                </form>
                <c:if test="${param.error != null}">
                    <p style="color: red;">Invalid username or password.</p>
                </c:if>
                <c:if test="${param.logout != null}">
                    <p style="color: green;">You have been logged out.</p>
                </c:if>
            </article>
        </main>
    </body>

</html>
