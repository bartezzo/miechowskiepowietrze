package pl.tobzzo.miechowskiepowietrze.di


import dagger.Binds
import dagger.Module
import pl.tobzzo.miechowskiepowietrze.example.ExampleInterface
import pl.tobzzo.miechowskiepowietrze.example.MpowExampleImplementation
import pl.tobzzo.miechowskiepowietrze.network.MpowNetworkComponent
import pl.tobzzo.miechowskiepowietrze.network.NetworkComponent

@Module
abstract class MainModule {

  @Binds
  internal abstract fun bindExample(example: MpowExampleImplementation): ExampleInterface
}
