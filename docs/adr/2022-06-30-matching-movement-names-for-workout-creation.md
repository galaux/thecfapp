## Matching Movement names for Workout creation

When creating a Workout from free text input, I need to match _tokens_ such as "Clean and Jerk", "clean'n jerk", "C&J" etc to the _lemma_ "Clean and Jerk".

A bit of reading shows this seems to be called [_Normalization_](normalization) and can be achieved with two techniques:
- Lemmatization
  > Stemming usually refers to a crude heuristic process that chops off the ends of words in the hope of achieving this goal correctly most of the time, and often includes the removal of derivational affixes.
- Stemming
  > Lemmatization usually refers to doing things properly with the use of a vocabulary and morphological analysis of words, normally aiming to remove inflectional endings only and to return the base or dictionary form of a word, which is known as the lemma.

From the 2 (lemmatization vs stemming), it seems what I want is lemmatization because stemming will never be able to understand that "C&J" is "clean and Jerk"
I thus think I need to do lemmatization with my custom "dictionnary"
This seems to be what
- [Core NLP](core-nlp) does with _Custom Annotators_
- [Apache OpenNLP](open-nlp)
Looking at the two code snippets, it – expectedly – boils down to declaring a hash-map of token → lemma. Using such libraries ust make a lot of sense in the larger context of NLP of a full text but here, I'm just interested in lemmatization. It thus appears far-fetch to me to use such a library where I should get good results with a mere hash-map lookup.
Moreover, the numbers are not big: if I have 500 different movements and all have 10 variations in naming, I will be left with a 5000 entry hash-map in memory which is far from huge.

## References

- [normalization]: https://towardsdatascience.com/introduction-to-natural-language-processing-for-text-df845750fb63#0c8d
- [core-nlp]: https://stanfordnlp.github.io/CoreNLP/new_annotator.html
- [open-nlp]: https://opennlp.apache.org/docs/2.0.0/manual/opennlp.html#tools.lemmatizer
