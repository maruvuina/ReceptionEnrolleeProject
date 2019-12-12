var checkBoxs = document.getElementsByClassName("checkbox");
function selectOnlyThis(id) {
    for (var i = 1; i <= checkBoxs.length; i++) {
        document.getElementById("check" + i).checked = false;
    }
    var check = document.getElementById(id);
    check.checked = true;
    document.getElementById("submit").value = check.value;
}
