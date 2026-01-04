<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chatter - Hello World</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
        <script src="https://unpkg.com/htmx.org@2.0.4"></script>
    </head>

    <body>
        <main class="container">
            <h1>Hello World from Spring Boot + JSP + SQLite!</h1>
            <p>Styled with Pico.css and ready for HTMX.</p>

            <article>
                <header>User Information</header>
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal != null}">
                        <p>
                            Welcome, <strong>${pageContext.request.userPrincipal.principal.name}</strong>
                            (${pageContext.request.userPrincipal.principal.email})!
                        </p>
                        <form action="/logout" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="secondary">Logout</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p>You are not logged in.</p>
                        <a href="/login" role="button">Login</a>
                    </c:otherwise>
                </c:choose>
            </article>
        </main>
    </body>

</html>