# hazard

A Clojure/ClojureScript library for getting random words, with optional length
requirements. The Clojure version includes a high-quality default word list.

[![Build Status][build-icon]][build-status]

[![Clojars Project][clojars-icon]][clojars-link]

## Usage

### Clojure

```clojure
(require '[hazard.core :refer :all])

; use the default wordlist
(words 2) ; ("orals" "yelping")
(words 2 {:min 8}) ; ("favorite" "hucksters")
(words 3 {:max 5}) ; ("shins" "miner" "item")
(words 2 {:min 8 :max 8}) ; ("kayakers" "jokester")

; provide a path to one
(words "my/list.txt" 3) ; ("my" "great" "words")

; or just provide a list
(words ["bear" "cat" "dog"] 2 {:max 3}) ; ("dog" "cat")

; get the number of candidate words the list was picked from
(:candidate-count (meta (words ["bear" "cat" "dog"] 2 {:max 3}))) ; 2
```

### ClojureScript

The ClojureScript version doesn't currently include a default wordlist, it's up
to you to provide one:

```clojurescript
(words ["bear" "cat" "dog"] 2 {:max 3}) ; ("dog" "cat")
```

## API

- **words**
    - `(words num-words)`
    - `(words coll num-words)`
    - `(words file-path num-words)`

**Note:** The ClojureScript version only supports the `(words coll num-words)`
arity.

Each arity can take an `opts` map as its last parameter, with the following
keys:

- `:min` the least of characters you'd like a returned word to contain
- `:max` the most characters you'd like a returned word to contain

If you ask for more items than can be provided from a given word set, an
exception is thrown:

```
user=> (require '[hazard.core :as hazard])
nil
user=> (hazard/words ["bear" "cat" "dog"] 3 {:max 3})

Exception More items requested than are available.  hazard.common/do-take-random-n (common.clj:7)
user=> 
```

## Implementation Notes

- The word list is read synchronously at the first call to `words`, same if you
  provide a file as a string. Any subsequent calls will use the file that's
  already been read into memory. If you need control over how the file is read,
  you can read it yourself and then pass a collection of words as the first
  parameter.
- A word list file is expected to be a list of words separated by newlines
- The default word list's resource file path is exported as a
  `clojure.java.io/file` at `hazard.core/default-words`.

## Test

Tests require that [PhantomJS][phantom] v2.0.0 or greater is installed, and
available on your `$PATH` as `phantomjs`:

```bash
$ lein test && lein doo phantom test once
```

## License

Copyright © 2015 Nathan Wittstock

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

### Grady Ward's Moby License

This project includes a wordlist taken from Grady Ward's Moby II, a list of 
words that has been placed in the public domain.

```
The Moby lexicon project is complete and has
been place into the public domain. Use, sell,
rework, excerpt and use in any way on any platform.

Placing this material on internal or public servers is
also encouraged. The compiler is not aware of any
export restrictions so freely distribute world-wide.

You can verify the public domain status by contacting

Grady Ward
3449 Martha Ct.
Arcata, CA  95521-4884
```

[build-status]: https://travis-ci.org/fardog/hazard
[build-icon]: http://img.shields.io/travis/fardog/hazard/master.svg?style=flat-square
[clojars-link]: http://clojars.org/hazard
[clojars-icon]: http://clojars.org/hazard/latest-version.svg
[phantom]: http://phantomjs.org/
