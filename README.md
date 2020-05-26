# scala-unboxed-option

[![Build Status](https://travis-ci.org/bbjubjub2494/scala-unboxed-option.svg?branch=master)](https://travis-ci.org/bbjubjub2494/scala-unboxed-option)

Scala's `Option[+A]` type is really useful to accurately type optional values.
Its higher-order methods like `map` and `getOrElse`, as well as its pattern
matching capabilities, make it a pleasure to deal with.
However, as is well known, it incurs the cost of boxing every present value in
an instance of `Some`.
In some cases, this can cause performance issues.

`scala-unboxed-option`'s `UOption[+A]`, which stands for Unboxed Option, solves
this problem.
It retains the full API of `Option[+A]`, including the fact that it is type
parametric.

## License

`scala-unboxed-option` is released under the BSD 3-clause license.
See [LICENSE.txt](./LICENSE.txt) for details.
