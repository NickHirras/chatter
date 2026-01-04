<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chatter - Rooms</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
        <script src="https://unpkg.com/htmx.org@2.0.4"></script>
    </head>

    <body>
        <main class="container">
            <header>
                <hgroup>
                    <h1>Chatter</h1>
                    <p>Welcome, <strong>
                            <c:out value="${pageContext.request.userPrincipal.principal.name}" />
                        </strong></p>
                </hgroup>
                <form action="/logout" method="post" style="margin-top: 1rem;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <button type="submit" class="secondary outline">Logout</button>
                </form>
            </header>

            <section>
                <h2>Chat Rooms</h2>

                <c:choose>
                    <c:when test="${not empty rooms}">
                        <c:forEach var="room" items="${rooms}">
                            <article>
                                <header>
                                    <strong>
                                        <c:out value="${room.name}" />
                                    </strong>
                                </header>
                                <p>
                                    <c:out value="${room.description}" />
                                </p>
                                <footer>
                                    <c:choose>
                                        <c:when test="${not room.privateRoom}">
                                            🌐 Public
                                        </c:when>
                                        <c:otherwise>
                                            🔒 Private
                                        </c:otherwise>
                                    </c:choose>
                                </footer>
                            </article>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <article>
                            <p>No rooms available.</p>
                        </article>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </body>

    </html>