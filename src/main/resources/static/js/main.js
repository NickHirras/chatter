function handleRoomChange(element) {
    // Deactivate all room items
    document.querySelectorAll('.room-item').forEach(el => {
        el.classList.remove('active');
    });

    // Activate the selected room item
    if (element) {
        element.classList.add('active');
    }

    // Scroll to the bottom of the message area
    const messageArea = document.querySelector('.message-area');
    if (messageArea) {
        messageArea.scrollTop = messageArea.scrollHeight;
    }
}

document.body.addEventListener('htmx:responseError', function(evt) {
    window.alert('Error: ' + evt.detail.xhr.status);
});
