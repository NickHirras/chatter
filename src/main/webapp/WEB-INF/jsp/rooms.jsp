<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chatter - Rooms</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
        <link rel="stylesheet" href="/css/style.css">
        <script src="https://unpkg.com/htmx.org@2.0.4"></script>
        <script src="/js/ui.js"></script>
    </head>

    <body>
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
                                <a href="#" class="room-item">
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
            <main class="main-content">
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

                    <c:if test="${not empty rooms}">
                        <div class="grid">
                            <c:forEach var="room" items="${rooms}" end="2">
                                <article>
                                    <header><strong>
                                            <c:out value="${room.name}" />
                                        </strong></header>
                                    <p>
                                        <c:out value="${room.description}" />
                                    </p>
                                    <footer>
                                        <small>
                                            <c:choose>
                                                <c:when test="${not room.privateRoom}">🌐 Public</c:when>
                                                <c:otherwise>🔒 Private</c:otherwise>
                                            </c:choose>
                                        </small>
                                    </footer>
                                </article>
                            </c:forEach>
                        </div>
                    </c:if>
                </section>
            </main>
        </div>
    </body>

    </html>