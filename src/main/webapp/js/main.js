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
        .then(data => {
            if (!data.success) {
                if (data.message === 'Please login first') {
                    window.location.href = `${contextPath}/login`;
                } else {
                    alert(data.message || 'Failed to add product to cart.');
                }
            } else {
                // 成功
                alert(data.message || 'Product added to cart successfully.');
                window.location.reload(); // 或者你想更新局部DOM
            }
        })

}

function removeFromCart(productId) {
    fetch(`${contextPath}/shopping/cart`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `action=remove&productId=${productId}`
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();  // 解析JSON，而不是直接显示在页面
        })
        .then(data => {
            if (!data.success) {
                if (data.message === 'Please login first') {
                    window.location.href = `${contextPath}/login`;
                } else {
                    alert(data.message || 'Remove failed');
                }
            } else {
                // 移除成功
                alert(data.message || 'Removed from cart');
                // 刷新页面或修改DOM
                window.location.reload();
            }
        })
        .catch(err => {
            console.error('Error:', err);
            alert('An error occurred while removing the product from cart.');
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
            // 如果后端重定向(response.redirected==true)，也可处理:
            if (response.redirected) {
                window.location.href = response.url;
                return;
            }
            return response.json();
        })
        .then(data => {
            if (data && !data.success) {
                // 失败情况
                if (data.message === 'Please login first') {
                    // 跳转登录
                    window.location.href = `${contextPath}/login`;
                } else {
                    alert(data.message || 'Checkout failed.');
                }
            } else {
                // 成功
                // data.success === true
                // data.message => "Checkout successful"
                alert(data.message || 'Checkout succeeded!');
                // 刷新页面或跳转到订单页
                window.location.reload();
                // 或: window.location.href = `${contextPath}/shopping/orders`;
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




















