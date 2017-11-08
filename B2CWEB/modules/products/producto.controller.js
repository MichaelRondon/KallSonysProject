var app = angular.module('myapp', ['infinite-scroll']);

app.controller('ProductoController', function($scope, Reddit) {
  $scope.reddit = new Reddit();
});

// Reddit constructor function to encapsulate HTTP and pagination logic
app.factory('Reddit', function($http) {
  var Reddit = function() {
    this.items = [];
    this.busy = false;
    this.after = '';
	this.page = 0;
	this.total_pages = 0;
  };

  Reddit.prototype.nextPage = function() {
    if (this.busy) return;
    this.busy = true;

	var url = "http://laptop-michael:7076/api/producto/buscar/scroll?page=" + this.page + '&items_per_page=20';
    //var url = "https://api.reddit.com/hot?after=" + this.after + "&jsonp=JSON_CALLBACK";
    //$http.jsonp(url).success(function(data) {
    $http.get(url).success(function(data) {
      var items = data.productos;
	  this.page = data.page.number + 1;
	  this.total_pages = data.page.total_pages;
      for (var i = 0; i < items.length; i++) {
        this.items.push(items[i]);
      };
      this.after = "t3_" + this.items[this.items.length - 1].id;
      this.busy = false;
    }.bind(this));
  };

  return Reddit;
});