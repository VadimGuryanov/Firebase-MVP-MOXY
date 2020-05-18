package kpfu.itis.firebasemvp.presenter.auth.di

import dagger.BindsInstance
import dagger.Subcomponent
import kpfu.itis.firebasemvp.presenter.auth.forgot_pass.ForgotPasswordFragment
import kpfu.itis.firebasemvp.presenter.auth.registration.RegistrationFragment
import kpfu.itis.firebasemvp.presenter.auth.signin.SignInFragment
import kpfu.itis.firebasemvp.presenter.auth.telephone.TelephoneFragment

@AuthScope
@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun signInFragment(signInFragment: SignInFragment) : Builder
        fun build() : AuthComponent
    }

    fun inject(signInFragment: SignInFragment)
    fun inject(telephoneSignIn: TelephoneFragment)
    fun inject(registrationFragment: RegistrationFragment)
    fun inject(forgotPasswordFragment: ForgotPasswordFragment)

}
