window.onload = function()  {
    var buttons = document.getElementsByClassName("btn");
    if (buttons.length > 0) {
        for (var i = 0; i < buttons.length; i++) {
            buttons[i].click = receiver(buttons[i]);
        }
    }
};

function receiver(val) {
    return function () {
        alert("click--> " + val.value());
    };
}