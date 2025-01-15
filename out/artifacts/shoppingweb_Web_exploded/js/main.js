const contextPath = '/shoppingweb_Web_exploded';

function addToCart(productId) {
    if (!productId) {
        console.error('Product ID is required');
        return;
    }

    fetch(`${contextPath}/shopping/cart`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `action=add&productId=${productId}`
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            if (response.redirected) {
                window.location.href = response.url;
                return;
            }
            return response.json();
        })
        .then(data => {
            if (data && !data.success) {
                alert(data.message || 'Failed to add product to cart.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while adding the product to cart.');
        });
}

function removeFromCart(productId) {
    if (!productId) {
        console.error('Product ID is required');
        return;
    }

    fetch(`${contextPath}/shopping/cart`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `action=remove&productId=${productId}`
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data && data.success) {
                window.location.reload();
            } else {
                throw new Error(data.message || 'Failed to remove product from cart.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message || 'An error occurred while removing the product from cart.');
        });
}

function checkout() {
    fetch(`${contextPath}/shopping/cart`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'action=checkout'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            if (response.redirected) {
                window.location.href = response.url;
                return;
            }
            return response.json();
        })
        .then(data => {
            if (data && !data.success) {
                if (data.message === 'Please login first') {
                    window.location.href = `${contextPath}/login`;
                } else {
                    alert(data.message || 'Checkout failed.');
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred during checkout.');
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




















