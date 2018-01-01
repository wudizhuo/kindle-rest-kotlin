var loc = document.location;
var uri = {
  spec: loc.href,
  host: loc.host,
  prePath: loc.protocol + "//" + loc.host,
  scheme: loc.protocol.substr(0, loc.protocol.indexOf(":")),
  pathBase: loc.protocol + "//" + loc.host + loc.pathname.substr(0, loc.pathname.lastIndexOf("/") + 1)
};
var article = new Readability(uri, document).parse();

var meta = document.createElement('meta');
meta.httpEquiv = "Content-Type";
meta.content = "text/html; charset=utf-8";
document.getElementsByTagName('head')[0].appendChild(meta);

document.body.innerHTML = article.content;
return JSON.stringify(article);
