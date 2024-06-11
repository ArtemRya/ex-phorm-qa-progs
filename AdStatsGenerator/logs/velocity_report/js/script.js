var tableForColorize = "table.colorize";
var errorDetails = [
    ["td.diff", "tr.diff"],
    ["td.cookies","tr.cookies"],
    ["td.stacktrace","tr.stacktrace"]
];
var errorsTable = "table.errorDetails";
var screenShot = "td.screenshot:not(:has(img.faded))";
var htmlSource = "td.htmlsource:not(:has(img.faded))";
var legendTable = "table.legend";
var evenClass = "even";
var elementsForCopy = "tr.stacktrace div, .header div.value";
var hiddenClassesHash = {};
var reportType = "#reportType";
var suites = "suites";

$().ready(function() {
    var logLevels = $('#TRACE, #DEBUG, #INFO, #WARN, #ERROR, #FATAL');
    //Hide summary legend table on start
    $(legendTable + " tbody:visible").hide();

    //Show summary legend table on mouseOver
    $(legendTable).mouseover(function() {
        $(legendTable).addClass("darkTable");
        $(legendTable + " tbody:hidden").show();
    });

    //Hide summary legend table on mouseOut
    $(legendTable).mouseout(function() {
        $(legendTable).removeClass("darkTable");
        $(legendTable + " tbody:visible").hide();
    });

    //Set checkbox state to checked
    //Add function for checkbox to toggle logLevel
    logLevels.click(function() {
        onCheck(this);
    })
            .each(function() {
                var curr = $(this);
                var logLevel = curr.attr('id');
                var toCheck = $.cookie(logLevel) || "on";

                if (toCheck != "on") {
                    curr.removeAttr('checked')
                }
                updateVisibility(this);
                if (!$('tr.' + logLevel).length) {
                    curr.attr('disabled', true);
                    curr.parent('label').css("color", "gray");
                }
            });

    //Colorize table rows
    colorizeTable(tableForColorize);

    //Hide cookies and stacktrace info (rows)
    $.each(errorDetails, function(idx, item) {
        $(item[1]).hide();
    });

    //Select element by click
    $(elementsForCopy).click(
            function() {
                fnSelect(this);
            }).attr('title', 'Use "leftClick" to select');

    //Add show/hide function for cookies and stacktrace
    $.each(errorDetails, function(idx, item) {
        $(item[0]).each(function() {
            $(this).click(function() {
                $(item[1], $(this).parents('table:eq(0)')).toggle();
            });
        });
    });

    //Add show function for screenshot
    $(screenShot).click(function() {
        showScreenshot($(this).children('img').attr('alt'));
    });

    //Add show function for htmlSource
    $(htmlSource).click(function() {
        showHtmlSource($(this).children('img').attr('alt'));
    });

    $(errorsTable).parent().addClass("paddingless");
});

function colorizeTable(table) {
    $(table + ' tr').removeClass(evenClass)
            .filter(
            function() {
                return !(this.className in hiddenClassesHash)
            }).filter(':not(td tr):even').addClass(evenClass);
}

function updateVisibility(logLevelElement) {
    var logLevel = logLevelElement.id;

    if (logLevelElement.checked) {
        $('table#logTable').removeClass(logLevel + "off");
        delete hiddenClassesHash[logLevel];
    } else {
        $('table#logTable').addClass(logLevel + "off");
        hiddenClassesHash[logLevel] = null;
    }
    $.cookie(logLevelElement.id, logLevelElement.checked ? "on" : "off", { path: '/'});
}

function onCheck(logLevelElement) {
    updateVisibility(logLevelElement);
    colorizeTable(tableForColorize);
}

function showHtmlSource(file) {
    window.open(file, 'HtmlSource', 'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,copyhistory=no,resizable=yes');
}

function showScreenshot(file) {
    var win = window.open(file, 'Screenshot', 'toolbar=no,location = no,directories = no,status = no,menubar = no,copyhistory = no,resizable = yes,scrollbars = yes');
    win.innerHTML = "<img alt=\"error screenshot\" src=\"" + file + "\">";
}

function fnSelect(obj) {
    fnDeSelect();
    var range;
    if (document.selection) {
        range = document.body.createTextRange();
        range.moveToElementText(obj);
        range.select();
    }
    else {
        if (window.getSelection) {
            range = document.createRange();
            range.selectNode(obj);
            window.getSelection().addRange(range);
        }
    }
}

function fnDeSelect() {
    if (document.selection) {
        document.selection.empty();
    }
    else {
        if (window.getSelection) {
            window.getSelection().removeAllRanges();
        }
    }
}

//reload required frame with new url.
function reloadFrame(frameElement, file) {
    frameElement.location = file;
}

function getReportType() {
    return $.cookie(reportType) || "suites";
}

function reloadSuitesFrame() {
    reloadFrame(parent.suitesFrame, 'suites.html');
}

function reloadTestsFrame() {
    reloadFrame(parent.testsFrame, 'tests.html');
}

function reloadRightFrame(file) {
    reloadFrame(parent.rightFrame, file);
}

function reloadErrorsFrame() {
    reloadFrame(parent.errorsFrame, 'errors.html');
}

// reload 'Suites' frames. Force is optional parameter (means that frames are force reloaded or they are reloaded only
// if they have different locations)
function backToSummary() {
    if (getReportType() == suites) {
        reloadSuitesFrame();
        reloadTestsFrame();
        reloadRightFrame('suites_summary.html');
    } else {
        reloadErrorsFrame();
        reloadRightFrame('errors_summary.html');
    }
}

function historyBack() {
    history.back();
}
