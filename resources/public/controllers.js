var r2r = angular.module('r2r', ['ngRoute']);

r2r.config(['$routeProvider', function ($routeProvider) {

    $routeProvider.when('/New', {
        templateUrl: 'partials/new-learning.html',
        controller: 'R2RController'
    }).otherwise({
            redirectTo: '/New'
        });

}]);

r2r.controller('R2RController', function ($scope, $http, $location) {


    $scope.saveItem = function () {
        $http.post("/learnings", {"question": $scope.question, "answer": $scope.answer}).success(function (data) {

        });

    }
});
