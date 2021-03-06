/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This file is part of eXo Acceptance Webapp.
 *
 * eXo Acceptance Webapp is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Acceptance Webapp software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Acceptance Webapp; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
var app = angular.module('repository', ['ngRoute', 'restangular', 'ui.bootstrap']).
    config(function ($routeProvider, RestangularProvider) {
             $routeProvider.
                 when('/', {
                        controller: ListCtrl,
                        templateUrl: 'list.html'
                      }).
                 when('/edit/:repositoryId', {
                        controller: EditCtrl,
                        templateUrl: 'detail.html',
                        resolve: {
                          repository: function (Restangular, $route) {
                            return Restangular.one('repository', $route.current.params.repositoryId).get();
                          }
                        }
                      }).
                 when('/new', {controller: CreateCtrl, templateUrl: 'detail.html'}).
                 otherwise({redirectTo: '/'});
             RestangularProvider.setBaseUrl('/api/admin/vcs');
           });

var loadCredentialsList = function ($http, $scope) {
  $http.get('/api/admin/credential').
      success(function (data, status, headers, config) {
                $scope.credentials = data;
              }).
      error(function (data, status, headers, config) {
              // called asynchronously if an error occurs
              // or server returns response with an error status.
            });
};

app.filter('filterByNameOrType', function () {
  /* array is first argument, each additional argument is prefixed by a ":" in filter markup */
  return function (dataArray, searchTerm) {
    if (!dataArray) return;
    /* when term is cleared, return full array */
    if (!searchTerm) {
      return dataArray
    } else {
      /* otherwise filter the array */
      var term = searchTerm.toLowerCase();
      return dataArray.filter(function (item) {
        return item.name.toLowerCase().indexOf(term) > -1 || item.type.toLowerCase().indexOf(term) > -1;
      });
    }
  }
});

// Controllers
function ListCtrl($scope, Restangular, $log) {
  $scope.alerts = [];
  $scope.closeAlert = closeAlert;
  $scope.repositories = [];
  Restangular.all("repository").getList().then(function (result) {
    // Everything is ok.
    $scope.repositories = result;
  }, function (response) {
    displayErrors($scope, response, $log);
  });
}

function CreateCtrl($scope, $location, Restangular, $log, $http) {
  $scope.alerts = [];
  $scope.closeAlert = closeAlert;
  $scope.repository = {};
  $scope.repository.type = "GIT";
  $scope.addRemote = function () {
    if (typeof $scope.repository.remoteRepositories == "undefined") {
      // Create an empty array
      $scope.repository.remoteRepositories = [];
    }
    $scope.repository.remoteRepositories.push({});
  };
  $scope.removeRemote = function ($index) {
    $scope.repository.remoteRepositories.splice($index, 1);
  };
  $scope.noRemote = function () {
    return typeof $scope.repository.remoteRepositories === "undefined" || $scope.repository.remoteRepositories.length == 0;
  };
  loadCredentialsList($http, $scope);

  $scope.save = function () {
    Restangular.all('repository').post($scope.repository).then(function (repository) {
      $location.path('/');
    }, function (response) {
      displayErrors($scope, response, $log);
    });
  }
}

function EditCtrl($scope, $location, Restangular, repository, $log, $http) {
  $scope.alerts = [];
  $scope.closeAlert = closeAlert;
  var original = repository;
  $scope.repository = Restangular.copy(original);
  $scope.addRemote = function () {
    if (typeof $scope.repository.remoteRepositories == "undefined") {
      // Create an empty array
      $scope.repository.remoteRepositories = [];
    }
    $scope.repository.remoteRepositories.push({});
  };
  $scope.removeRemote = function ($index) {
    $scope.repository.remoteRepositories.splice($index, 1);
  };
  $scope.noRemote = function () {
    return typeof $scope.repository.remoteRepositories === "undefined" || $scope.repository.remoteRepositories.length == 0;
  };
  loadCredentialsList($http, $scope);

  $scope.isClean = function () {
    return angular.equals(original, $scope.repository);
  };


  $scope.destroy = function () {
    original.remove().then(function () {
      $location.path('/');
    }, function (response) {
      displayErrors($scope, response, $log);
    });
  };

  $scope.save = function () {
    $scope.repository.put().then(function () {
      $location.path('/');
    }, function (response) {
      displayErrors($scope, response, $log);
    });
  };

}
