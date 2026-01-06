<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
    <c:when test="${currentPage + 1 < totalPages}">
        <div id="load-more-trigger"
             hx-get="/rooms/${room_id}/messages/older?page=${currentPage + 1}"
             hx-trigger="intersect once"
             hx-swap="outerHTML"
             hx-indicator="#loading-spinner">
        </div>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 1rem; color: #888;">
            <p>No more messages</p>
        </div>
    </c:otherwise>
</c:choose>

<c:forEach var="message" items="${messages}">
    <c:set var="msg" value="${message}" scope="request" />
    <jsp:include page="_message_item.jsp" />
</c:forEach>
