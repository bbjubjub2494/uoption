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

## API differences
- `UOption` cannot be subtyped. There is no equivalent to `Some[A]` or `None.type`
- Some methods from `AnyRef` such as `isInstanceOf` or `toString` break the abstraction.
- `UOption` isn't a subtype of the `Product` interface.

## Notes
`UOption` values are fully serializable unless they wrap a value that isn't. They also take less bytes in most cases due to the absence of a wrapper object.

`UOption` doesn't introduce `null` to the value space. Unless a null value is wrapped, you wont risk a `NullPointerException`.

`AnyVal` subtypes are still boxed inside of an `UOption`.

`UOption` can nest. In the cased of wrapped `UNone`, allocation can be incurred.
