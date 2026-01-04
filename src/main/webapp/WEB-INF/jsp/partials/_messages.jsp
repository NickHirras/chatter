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

        <section class="message-area">
            <c:choose>
                <c:when test="${not empty messages}">
                    <c:forEach var="message" items="${messages}">
                        <article style="margin-bottom: 1rem; padding: 0.75rem;">
                            <header
                                style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem; padding: 0; border: none; background: none;">
                                <strong>
                                    <c:out value="${message.createdBy.name}" />
                                </strong>
                                <small style="color: #888;">
                                    <fmt:formatDate value="${message.createdAtDate}" pattern="MMM d, h:mm a" />
                                </small>
                            </header>
                            <p style="margin: 0;">
                                <c:out value="${message.content}" />
                            </p>
                        </article>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; padding: 2rem; color: #888;">
                        <p>No messages yet. Be the first to say hello!</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>