var r2r = angular.module('r2r', ['ngRoute']);

r2r.config(['$routeProvider', function ($routeProvider) {

}]);

r2r.controller('R2RController', function ($scope, $http, $location) {


    $scope.saveItem = function () {
        $http.post("/learnings", {"question": $scope.question, "answer": $scope.answer}).success(function (data) {

        });

    }
});
