var page = require('webpage').create();

page.settings.userAgent = 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36';

page.onError = function(msg, trace) {
    var msgStack = ['ERROR: ' + msg];
    if (trace && trace.length) {
        msgStack.push('TRACE:');
        trace.forEach(function(t) {
        msgStack.push(' -> ' + t.file + ': ' + t.line + (t.function ? ' (in function "' + t.function +'")' : ''));
    });
  }
  console.error(msgStack.join('\n'));
};

page.onResourceError = function(resourceError) {
      console.log('Unable to load resource (#' + resourceError.id + ' URL:' + resourceError.url + ')');
      console.log('Error code: ' + resourceError.errorCode + '. Description: ' + resourceError.errorString);
};
page.onResourceTimeout = function(request) {
      console.log('Response Timeout (#' + request.id + '): ' + JSON.stringify(request));
};

page.open('https://xueqiu.com/8563172815/98452396', function() {
  setTimeout(function() {
     page.render('github.png');
     phantom.exit();
  }, 1500);
});