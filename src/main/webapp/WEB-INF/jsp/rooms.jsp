<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chatter - Rooms</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
        <link rel="stylesheet" href="/css/style.css">
        <script src="https://cdn.jsdelivr.net/npm/htmx.org@2.0.8/dist/htmx.min.js" integrity="sha384-/TgkGk7p307TH7EXJDuUlgG3Ce1UVolAOFopFekQkkXihi5u/6OCvVKyz1W+idaz" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/htmx-ext-preload@2.1.2" integrity="sha384-PRIcY6hH1Y5784C76/Y8SqLyTanY9rnI3B8F3+hKZFNED55hsEqMJyqWhp95lgfk" crossorigin="anonymous"></script>
        <script src="/js/ui.js"></script>
    </head>

    <body hx-ext="preload">
        <div class="app-container">
            <!-- Mobile Sidebar Overlay -->
            <div id="sidebar-overlay" class="sidebar-overlay"></div>

            <!-- Sidebar -->
            <aside id="sidebar" class="sidebar">
                <div class="sidebar-header">
                    <h4 style="margin: 0;">Chatter</h4>
                </div>

                <div class="user-widget">
                    <div class="user-info">
                        <span>
                            <c:out value="${pageContext.request.userPrincipal.principal.name}" />
                        </span>
                    </div>
                    <form action="/logout" method="post" style="margin: 0;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <button type="submit" class="secondary outline contrast"
                            style="padding: 0.25rem 0.5rem; font-size: 0.8rem;">Logout</button>
                    </form>
                </div>

                <nav class="room-list">
                    <small
                        style="padding: 0 0.75rem; color: #888; text-transform: uppercase; font-weight: bold;">Rooms</small>
                    <c:choose>
                        <c:when test="${not empty rooms}">
                            <c:forEach var="room" items="${rooms}">
                                <a href="#" class="room-item ${room.id == param.roomId ? 'active' : ''}"
                                    preload="mouseover"
                                    hx-get="/rooms/${room.id}/messages" hx-target="#main-content" hx-swap="innerHTML"
                                    hx-push-url="?roomId=${room.id}"
                                    hx-on::after-request="document.querySelectorAll('.room-item').forEach(el => el.classList.remove('active')); this.classList.add('active');">
                                    <span style="margin-right: 0.5rem;">#</span>
                                    <c:out value="${room.name}" />
                                </a>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p style="padding: 0.75rem; font-size: 0.9rem;">No rooms available.</p>
                        </c:otherwise>
                    </c:choose>
                </nav>
            </aside>

            <!-- Main Content -->
            <main id="main-content" class="main-content">
                <c:choose>
                    <c:when test="${not empty room}">
                        <jsp:include page="partials/_messages.jsp" />
                    </c:when>
                    <c:otherwise>
                        <header class="content-header">
                            <button id="sidebar-toggle" class="sidebar-toggle">
                                ☰
                            </button>
                            <h5 style="margin: 0;">Welcome to Chatter</h5>
                        </header>

                        <section class="message-area">
                            <article>
                                <header>Getting Started</header>
                                <p>Select a room from the sidebar to start chatting!</p>
                                <footer>
                                    <small>You are currently in the general overview.</small>
                                </footer>
                            </article>
                        </section>
                    </c:otherwise>
                </c:choose>
            </main>
        </div>
    </body>

    </html>
