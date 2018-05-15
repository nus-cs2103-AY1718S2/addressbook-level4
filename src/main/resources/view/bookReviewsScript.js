// show only the reviews section
var meta = document.getElementById('bookMeta');
var reviews = document.getElementById('other_reviews');
if (meta && reviews) {
    document.body.innerHTML = '<div class="siteHeader__logo"></div>' +
        '<div id="mainContent" style="padding: 0 12px 5px; max-width: 750px;"></div>';
    var mainContent = document.getElementById('mainContent');
    var header = document.createElement('div');
    header.style = 'float: right;';
    header.appendChild(meta);
    mainContent.appendChild(header);
    mainContent.appendChild(reviews);
}

// disables links, given a class name
function disable(clazz) {
    var eles = document.getElementsByClassName(clazz);
    for (var i = 0; i < eles.length; i++) {
        eles[i].onclick = (e) => e.preventDefault();
    }
}

// processes the page to remove comment footers and disable certain links
function processPage() {
    var r = document.getElementsByClassName('reviewFooter');
    for (var i = 0; i < r.length; i++) {
        var lc = r[i].querySelector('.likesCount');
        if (lc) { r[i].innerHTML = lc.innerHTML; } else { r[i].innerHTML = ''; }
    }
    disable('user');
    disable('reviewDate');
    disable('imgcol');
    disable('actionLinkLite');
}

// executes processPage after the completion of every ajax call
XMLHttpRequest.prototype.realSend = XMLHttpRequest.prototype.send;
XMLHttpRequest.prototype.send = function (value) {
    this.addEventListener('load', () => {
        try {
            processPage();
        } catch (e) {}
    }, false);
    this.realSend(value);
};

try {
    processPage();
} catch (e) {}
