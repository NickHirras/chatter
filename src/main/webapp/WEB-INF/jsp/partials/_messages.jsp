<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

        <header class="content-header">
            <button id="sidebar-toggle" class="sidebar-toggle">☰</button>
            <h5 style="margin: 0;">#
                <c:out value="${room.name}" />
            </h5>
            <small style="margin-left: 1rem; color: #888;">
                <c:out value="${room.description}" />
            </small>
        </header>

        <section class="message-area" style="display: flex; flex-direction: column-reverse;">
            <div id="message-list-container" style="display: flex; flex-direction: column-reverse;">
                <c:choose>
                    <c:when test="${not empty messages}">
                        <jsp:include page="_message_list.jsp" />
                    </c:when>
                    <c:otherwise>
                        <div style="text-align: center; padding: 2rem; color: #888;">
                            <p>No messages yet. Be the first to say hello!</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="loading-spinner" class="htmx-indicator" style="text-align: center; display: none;">
                <p>Loading...</p>
            </div>
        </section>