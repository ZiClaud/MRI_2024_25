// - Wildcard Searches
// tes* (test - tests - tester)
// te?t (test – text)
// te*t (tempt)
// tes?

// - Fuzzy Searches (Levenshtein Distance) (TERM)
// roam~ (foam – roams)
// roam~0.8

// - Range Searches
// mod_date:[20020101 TO 20030101]
// title:{Aida TO Carmen}

// - Proximity Searches (PHRASE)
// "jakarta apache"~10

// - Boosting a Term
// jakarta^4 apache - ("jakarta" is x4 times more important than "apache")
// "jakarta apache"^4 "Apache Lucene" - ("jakarta apache" is x4 times more important than "Apache Lucene")

// - Boolean Operator
// NOT, OR, AND
// (+) required operator
//      title:(+return +"pink panther")
// (-) prohibit operator
//      Escaping Special Characters by \
