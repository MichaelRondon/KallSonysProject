'use strict';

// declare modules
angular.module('Authentication', []);
angular.module('Home', []);
angular.module('Products', []);

angular.module('myapp', [
    'Authentication',
    'Home', 'Products',
    'ngRoute',
    'ngCookies'
])

.config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/login', {
            controller: 'usercontroller',
            templateUrl: 'modules/authentication/views/login.html'
        })

		.when('/Recovery', {
            controller: 'recoveryUserController',
            templateUrl: 'modules/authentication/views/recovery.html'
        })
		
		.when('/Create', {
            controller: 'usersController',
            templateUrl: 'modules/authentication/views/create.html'
        })
		
		.when('/RestoreAccount', {
            controller: 'restoreAccountController',
            templateUrl: 'modules/authentication/views/restoreAccount.html'
        })
		
		.when('/Content', {
            controller: 'productsController',
            templateUrl: 'modules/products/views/content.html'
        })
		
		.when('/ContentProducts', {
            controller: 'controller_ContentProduct',
            templateUrl: 'modules/products/views/contentProduct.html'
        })
		
        .when('/', {
            controller: 'HomeController',
            templateUrl: 'modules/home/views/home.html'
        })
		
		.otherwise({ redirectTo: '/login' });
}])

.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && 
				$location.path() !== '/Recovery' && 
				$location.path() !== '/Create' && 
				$location.path() !== '/RestoreAccount' && 
				$location.path() !== '/Content' && 
				$location.path() !== '/ContentProducts' && 
				!$rootScope.globals.currentUser) {
                $location.path('/login');
            }
        });
    }]);
	

	// --------------- TEXTSEARCH --------------------
angular.module('myapp')
.controller("headercontroller", function($scope){  
      $scope.countryList = [  
           "Televisor 32 Pulg - Sony", "Televisor 40 Pulg - Sony", "Televisor 50 Pulg - Lg", "Televisor 50 Pulg - Panasonic"  
      ];  
      $scope.complete = function(string){  
           $scope.hidethis = false;  
           var output = [];  
		   if(string != ""){
				angular.forEach($scope.countryList, function(country){  
                if(country.toLowerCase().indexOf(string.toLowerCase()) >= 0)  
                {  
                     output.push(country);  
                }  
             }); 
		   }
            
		   
		   $scope.filterCountry = output;  
      }  
      $scope.fillTextbox = function(string){  
           $scope.country = string;  
           $scope.hidethis = true;  
      }  
 });
 
 
 // angular.module('myapp')
 // .controller('PaginationDemoCtrl', function ($scope, $log) {
  // $scope.totalItems = 64;
  // $scope.currentPage = 4;

  // $scope.setPage = function (pageNo) {
    // $scope.currentPage = pageNo;
  // };

  // $scope.pageChanged = function() {
    // $log.log('Page changed to: ' + $scope.currentPage);
  // };

  // $scope.maxSize = 5;
  // $scope.bigTotalItems = 175;
  // $scope.bigCurrentPage = 1;
// });
