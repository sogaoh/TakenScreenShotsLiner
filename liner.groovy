def tableTdBody = [
        [C001:'001.png', C002:'002.png', C003:'003.png', C004:'004.png', C005:'005.png', ]
]

def writer = new FileWriter('index.html')
def html = new groovy.xml.MarkupBuilder(writer)
html.html {
    head {
        title 'Taken Screenshots'
    }
    body {
        h1 'Taken at ' + (new Date().format("yyyy/MM/dd(E) HH:mm"))   //時刻

        table(border:'1') {
            tr {
                th('C001')
                th('C002')
                th('C003')
                th('C004')
                th('C005')
            }
            tableTdBody.each{ content ->
                tr {
                    td(content['C001'])
                    td(content['C002'])
                    td(content['C003'])
                    td(content['C004'])
                    td(content['C005'])
                }
            }
        }
    }
}