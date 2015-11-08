# hazard

A Clojure library for getting random dictionary words; use the included
wordlist, or provide your own!

## Usage

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
