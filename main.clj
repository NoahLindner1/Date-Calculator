(defn get-month [date]
;gets the month of a given date and parses it based off a slash or a dash
(let [date (clojure.string/split date #"[-|/]")]
(Integer. (get date 0))))

(defn get-day [date]
;gets the day of a given date and parses it by a slash or a dash
(let [date (clojure.string/split date #"[-|/]")]
(Integer. (get date 1))))

(defn get-year [date]
;gets the year of a given date and parses it based off a slash or a dash
(let [date (clojure.string/split date #"[-|/]")]
(Integer. (get date 2))))

(defn is-leap-year [year]
;conditional that tests if it is a leap year or not
  (cond
    (zero? (mod (Integer. year) 400)) true
    (zero? (mod (Integer. year) 100)) false
    (zero? (mod (Integer. year) 4)) true
    :default false
  )
)

(defn return-map [year]
;returns a map of the the correct calendar based off if it is a leapyear or not
  (cond 
    (true?(is-leap-year year)) {1 31 2 29 3 31 4 30 5 31 6 30 7 31 8 31 9 30 10 31 11 30 12 31}
    :else {1 31 2 28 3 31 4 30 5 31 6 30 7 31 8 31 9 30 10 31 11 30 12 31}
  )
)

(defn get-days-in-month [month year]
;used to get the number of days in a month
  (let [num-days-in-months {1 31 2 28 3 31 4 30 5 31 6 30 7 31 8 31 9 30 10 31 11 30 12 31}
      num-days-in-months-leapyear (assoc num-days-in-months 2 29)]
    (if (is-leap-year year)
      (num-days-in-months-leapyear month)
      (num-days-in-months month))))

(defn test-years [year]
;this returns the amount of days in a year if it is a leap year, used in my get-days-between-years
  (cond
  (true? (is-leap-year year)) 366
  :else 365
  )
)

(defn same-year-tester[first-date second-date]
;this is a tester to see if the two dates given are in the same year so I can know when to use the same-year function
  (cond
    (zero?(- (get-year first-date) (get-year second-date)))true
    :defualt false
  )
)

(defn same-year[first-date second-date]
;this function handles the calculation under the scenario where the two dates given are in the same year
;does this by adding up how many days into the year each date is and then subtracting the second total from the first total, = days into year
  (let [first-month(get-month first-date)
    map-to-use(return-map (get-year first-date))
    first-day(get-day first-date)
    month-day-range-first(range 1 (get-month first-date))
    num-days-per-month(map map-to-use month-day-range-first)
    total-days-in-first-date(reduce + num-days-per-month)
    first-total(+ first-day total-days-in-first-date)

    second-month(get-month second-date)
    second-day(get-day second-date)
    month-day-range-second(range 1 (get-month second-date))
    num-days-per-month-second(map map-to-use month-day-range-second)
    total-days-in-second-date(reduce + num-days-per-month-second)
    second-total(+ second-day total-days-in-second-date)
    subbed-total(- second-total first-total)
  ]
subbed-total
  )
)

(defn get-days-into-second-year [first-date second-date]
;fuction that grabs the amount of days into the the second dates year
  (let[days-left-in-second-month(get-day second-date) 
    map-to-use(return-map(get-year second-date))
    month-range(range 1 (get-month second-date))
    num-days-per-month(map map-to-use month-range)
    days-between-months(reduce + num-days-per-month)
    the-sum(+ days-left-in-second-month days-between-months)
  ]
  the-sum
  )
)

(defn get-days-between-years [first-date second-date]
;this calculates the number of days between whole years of the two dates given ad returns that value
  (let [first-year(+(get-year first-date)1)
    second-year(get-year second-date)
    years-between(range first-year second-year)
    test-var(map test-years years-between)
    total-days(reduce + test-var)
    ]
  total-days
    )
)

(defn get-days-into-first-year [first-date second-date]
;adds up the amount of days that are left in the year from the first date that is given and returns that value
  (let [days-left-in-month (- (get-days-in-month (get-month first-date) (get-year first-date))(get-day first-date))
    first-month(get-month first-date)
    second-month(get-month second-date)
    map-to-use(return-map (get-year first-date))
    month-range(range (+ first-month 1) 13)
    num-days-per-month(map map-to-use month-range)
    days-between-months(reduce + num-days-per-month)
    the-sum(+ days-left-in-month days-between-months)
    ]
  the-sum
    )
)

(defn get-number-of-days-between([first-date second-date]
;function that can handle either the dates being based in or no dates being based in
;all return values are added up here to give the number of days between dates
  (if (pos?(-(get-year first-date)(get-year second-date)))  ;makes sure dates are chornological
    (println "Your dates are not chronological, please try again")
    (if(same-year-tester first-date second-date)  ;test to see if dates are in the same year
      (println "There are" (same-year first-date second-date)"day(s) between the two dates")
      (println "There are"(+(+ (get-days-into-first-year first-date second-date) (get-days-between-years first-date second-date))(get-days-into-second-year first-date second-date))"days between the two dates")
    )
  )
)
([]
(println "Enter in the first date")
(let [first-date(read-line)]
(println "Enter in the second date")
(let [second-date(read-line)]
(get-number-of-days-between first-date second-date)))))

(get-number-of-days-between)
(get-number-of-days-between "01/01/2000" "11/03/2017")
