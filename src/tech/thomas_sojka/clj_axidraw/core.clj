(ns tech.thomas-sojka.clj-axidraw.core
  (:require [libpython-clj.python :refer [py. py.- ] :as py]))
(py/initialize!)
(def axidraw (py/import-module "pyaxidraw"))
(def axi (py. (py.- axidraw "axidraw") "AxiDraw"))

(defn connect-interactive []
  (py. axi "interactive")
  (py. axi "connect"))
(defn moveto [x y]
  (py. axi "moveto" x y))
(defn lineto [x y]
  (py. axi "lineto" x y))
(defn disconnect []
  (py. axi "disconnect"))

(defn manual-cmd [cmd-name]
  (py. axi "plot_setup")
  (py/set-attr! (py.- axi "options") "mode" "manual")
  (py/set-attr! (py.- axi "options") "manual_cmd" cmd-name)
  (py. axi "plot_run"))
(defn disable-xy [] (manual-cmd "disable_xy"))
(defn raise-pen [] (manual-cmd "raise_pen"))
(defn lower-pen [] (manual-cmd "lower_pen"))

(defn plot [file-name]
  (py. axi "plot_setup" file-name)
  (py. axi "plot_run")
  (disable-xy))

(defn estimate [file-name]
  (py. axi "plot_setup" file-name)
  (py/set-attr! (py.- axi "options") "preview" true)
  (py/set-attr! (py.- axi "options") "report_time" true)
  (py. axi "plot_run"))

(comment
  (connect-interactive)
  (raise-pen)
  (lower-pen)
  (disable-xy)
  (lineto 10 10)
  (lineto 0 0)
  (moveto 2 2)
  (disconnect)
  (plot "resources/cubic-disarray.svg"))
