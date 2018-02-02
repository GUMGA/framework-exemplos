
let RegisterComponent = {
    bindings: {
    },
    template: require('./register.html'),
    controller: ['GumgaLoginService', '$timeout', '$scope', '$element', function (GumgaLoginService, $timeout, $scope, $element) {
        let ctrl = this;
        const params = GumgaLoginService.getUrlVars();

        var config = {
            apiKey: "AIzaSyBOev5icnmyLi8gacBt7Rd00yoi-E-isO8",
            authDomain: "kigi-193622.firebaseapp.com",
            databaseURL: "https://kigi-193622.firebaseio.com",
            projectId: "kigi-193622",
            storageBucket: "",
            messagingSenderId: "277774252950"
          };
          
        firebase.initializeApp(config);

        ctrl.config = {
            titleStepOne: angular.element('gl-login').attr('register-title-step-one') || 'Ebaaa!!!',
            subTitleStepOne: angular.element('gl-login').attr('register-subtitle-step-one') || 'Estamos muitos felizes por você topar o desafio ;)',
            textBtnStepOne: angular.element('gl-login').attr('register-text-btn-step-one') || 'Bora lá se registrar',

            titleStepTwo: angular.element('gl-login').attr('register-title-step-two') || 'agora você é quase parte da familia',
            subTitleStepTwo: angular.element('gl-login').attr('register-subtitle-step-two') || 'Seu ambiente virtual na Kigi vai ser incrível',
            textBtnStepTwo: angular.element('gl-login').attr('register-text-btn-step-two') || 'Continuar',

            logo: angular.element('gl-login').attr('register-logo')
        }

        ctrl.$onInit = () => {
            ctrl.step = 1;
            ctrl.user = {
                email: params['email']
            }
            ctrl.initAutoComplete();
            if(ctrl.user.email){
                ctrl.blurEmail();
            }
        }

        ctrl.initAutoComplete = () => {
            ctrl.user.address = {};
            var defaultBounds = new google.maps.LatLngBounds(
                new google.maps.LatLng(-33.8902, 151.1759),
                new google.maps.LatLng(-33.8474, 151.2631));

            var input = document.getElementById('cepInput');
            let autocomplete = new google.maps.places.Autocomplete(input, {});
            autocomplete.addListener('place_changed', () => {
                let place = autocomplete.getPlace();
                $timeout(() => {
                    ctrl.user.address.street = place.name;
                    place.address_components.forEach((address_component) => {
                        let index = address_component.types.indexOf('postal_code');
                        if(index != -1){
                            ctrl.user.address.zipCode = address_component.long_name;
                        }
                    });
                })
            });
        }

        ctrl.getFirstName = () => {
            return (ctrl.user.name || '').split(' ')[0];
        }

        ctrl.getOrganizationName = () => {
            return (ctrl.user.organization || '').charAt(0).toUpperCase() + (ctrl.user.organization || '').slice(1);
        }

        ctrl.isValidEmail = email => {
            return /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email);
        }

        ctrl.setTypePerson = (type) => {
            ctrl.user.typePerson = type;
            if (ctrl.user.typePerson == 'JURIDICA') {
                ctrl.continue();
            }
            if (ctrl.user.typePerson == 'FISICA') {
                ctrl.step = 6;
            }
        }

        ctrl.setQntPerson = (op) => {
            ctrl.user.organizationSize = op;
            ctrl.continue();
        }

        ctrl.setFollow = follow => {
            ctrl.user.follow = follow;
            ctrl.continue();
        }

        ctrl.isValidContinue = () => {
            let valid = true;
            if (ctrl.step == 1) {
                if (!ctrl.user.name) valid = false;
                if (!ctrl.user.email || !ctrl.isValidEmail(ctrl.user.email)) valid = false;
                if (!ctrl.user.password || !ctrl.user.confirmPassword) valid = false;
                if (ctrl.user.password != ctrl.user.confirmPassword) valid = false;
                if ((ctrl.user.password && ctrl.user.password.length < 3) || (ctrl.user.confirmPassword && ctrl.user.confirmPassword.length < 3)) valid = false;
            }
            if (ctrl.step == 2) {
                if (!ctrl.user.organization) valid = false;
            }
            if (ctrl.step == 6) {
                if (!ctrl.user.address.number) valid = false;
                if (!ctrl.user.address.zipCode) valid = false;
                if (!ctrl.user.address.street) valid = false;
            }
            return valid;
        };

        ctrl.blurEmail = () => {
            GumgaLoginService.verifiedUser(ctrl.user.email).then(resp => {
                ctrl.emailStatus = resp.data.response;
                if(ctrl.emailStatus == 'OK'){
                    $element.find('input[type="email"]').addClass('ng-touched');
                }
            }, err => {
            });
        };

        ctrl.continue = () => {
            ctrl.loading = true;
            $timeout(() => {
                delete ctrl.loading, 1500;
                ctrl.step++;
            }, 1500);
        }

        ctrl.back = () => {
            ctrl.step--;
        }

        ctrl.create = () => {            
            ctrl.loading = true;
            let user = {
                name: ctrl.user.name,
                email: ctrl.user.email,
                password: ctrl.user.password,
                followUp: ctrl.user.follow,
                organizationName: ctrl.user.organization,
                organizationContributors: ctrl.user.organizationSize,
                role: ctrl.user.type,
                type: ctrl.user.typePerson,
                address: ctrl.user.address,
            };
            firebase.database().ref('users/').push(user);
            GumgaLoginService.registerSaaS(user).then(resp => {
                if(resp.status == 200){
                    ctrl.step = 7;
                }
                delete ctrl.loading;
            }, err => {
                delete ctrl.loading;
            });
        }

        ctrl.goToLogin = () => {
            location.href = location.origin;
        }

    }]
}

export default RegisterComponent
