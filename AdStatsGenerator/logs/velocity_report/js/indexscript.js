var contentPane = "#leftSide";
var paneShownCookie = "paneShownCookie";
var reportType = "#reportType";
var suites = "suites";
var errors = "errors";
var notExecuted = "notExecuted";
var isPaneShown;

$().ready(function() {
    //show/hide the left test & suites frame
    var type = $.cookie(reportType) || suites;
    switch (type) {
        default:
        case suites:
            switchToSuites();
            break;
        case errors:
            switchToErrors();
            break;
        case notExecuted:
            switchToNotExecuted();
            break;
    }
    isPaneShown = !(($.cookie(paneShownCookie) || "off") == "on");
    togglePane();
});

function togglePane() {
    (isPaneShown) ? hidePane() : showPane();
}

function showPane() {
    $(contentPane).css("width", "400px");
    $.cookie(paneShownCookie, "on", {path: '/'});
    isPaneShown = true;
}

function hidePane() {
    $(contentPane).css("width", "1px");
    $.cookie(paneShownCookie, "off", {path: '/'});
    isPaneShown = false;
}

function switchToSuites() {
    $("#" + suites).show();
    $("#" + errors).hide();
    $("#" + notExecuted).hide();
    $("#suitesBtn img").addClass("selectedItem");
    $("#errorsBtn img").removeClass("selectedItem");
    $("#notExecutedBtn img").removeClass("selectedItem");

    $.cookie(reportType, suites, {path: '/'});
    reloadRightFrame('suites_summary.html');
}

function switchToErrors() {
    $("#" + suites).hide();
    $("#" + errors).show();
    $("#" + notExecuted).hide();
    $("#suitesBtn img").removeClass("selectedItem");
    $("#errorsBtn img").addClass("selectedItem");
    $("#notExecutedBtn img").removeClass("selectedItem");

    $.cookie(reportType, errors, {path: '/'});
    reloadRightFrame('errors_summary.html');
}

function switchToNotExecuted() {
    $("#" + suites).hide();
    $("#" + errors).hide();
    $("#" + notExecuted).show();
    $("#suitesBtn img").removeClass("selectedItem");
    $("#errorsBtn img").removeClass("selectedItem");
    $("#notExecutedBtn img").addClass("selectedItem");

    $.cookie(reportType, notExecuted, {path: '/'});
    reloadRightFrame('not_executed_test_cases.html');
}

function showSuites() {
    switchToSuites();
    showPane();
}

function showErrors() {
    switchToErrors();
    showPane();
}

function showNotExecuted() {
    switchToNotExecuted();
    showPane();
}