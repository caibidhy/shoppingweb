const contextPath = '/shopping';

function addToCart(productId) {
    fetch(`${contextPath}/cart`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `action=add&productId=${productId}`
    })
    .then(response => {
        if (response.ok) {
            alert('Product added to cart successfully!');
            location.reload();
        } else {
            alert('Failed to add product to cart.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred while adding the product to cart.');
    });
}

function removeFromCart(productId) {
    fetch(`${contextPath}/cart`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `action=remove&productId=${productId}`
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Failed to remove product from cart.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred while removing the product from cart.');
    });
}

// Add event listeners when the document is loaded
document.addEventListener('DOMContentLoaded', function() {
    // Handle mobile navigation toggle if needed
    const navToggle = document.querySelector('.nav-toggle');
    const navMenu = document.querySelector('.nav-menu');
    
    if (navToggle && navMenu) {
        navToggle.addEventListener('click', () => {
            navMenu.classList.toggle('active');
        });
    }
});

