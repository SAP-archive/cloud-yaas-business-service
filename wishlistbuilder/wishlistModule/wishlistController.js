var wishlistModule = angular.module('wishlistModule', ["builder"]);

wishlistModule.controller('wishlistController', function ($scope, Restangular, currentAccountId, currentProjectId, notificationManager, linkManager) {

     $scope.wishlistServiceUrl = Builder.currentWidget.settings.wishlistServiceUrl;
     Restangular.setBaseUrl($scope.wishlistServiceUrl);

     Restangular.all("wishlists").getList().then(function (response) {
         $scope.wishlists = Restangular.stripRestangular(response)
     });
 } );