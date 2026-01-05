<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

        <article class="message-item"
            style="margin-bottom: 1rem; padding: 0.75rem; border-left: 2px solid transparent;">
            <header
                style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem; padding: 0; border: none; background: none;">
                <strong>
                    <c:out value="${msg.createdBy.name}" />
                </strong>
                <small style="color: #888;">
                    <fmt:formatDate value="${msg.createdAtDate}" pattern="MMM d, h:mm a" />
                </small>
            </header>
            <p style="margin: 0;">
                <c:out value="${msg.content}" />
            </p>

            <c:if test="${not empty msg.replies}">
                <div class="reply-thread"
                    style="margin-left: 1.5rem; margin-top: 1rem; border-left: 2px solid var(--border-color); padding-left: 1rem;">
                    <c:forEach var="reply" items="${msg.replies}">
                        <c:set var="msg" value="${reply}" scope="request" />
                        <jsp:include page="_message_item.jsp" />
                    </c:forEach>
                </div>
            </c:if>
        </article>