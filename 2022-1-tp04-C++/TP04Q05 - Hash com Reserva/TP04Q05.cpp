#include <iostream>
#include <fstream>
#include <chrono>
#include <vector>
#include <string>
#include <algorithm>
#include <stdexcept>

#define FILEPATH_PREFIX "tmp/filmes/"
#define FILEPATH_PREFIX_2 "/tmp/filmes/"

struct Date {
    int day;
    int month;
    int year;
    std::string date_string;

    Date(){}

    Date(int d, int m, int y, std::string ds)
        :day(d), month(m), year(y), date_string(ds) {}

    bool operator==(const Date& d) const {
        return (day == d.day) &&
        (month == d.month) &&
        (year == d.year);
    }

    bool operator>(const Date& d) const {
        if (year < d.year) return false;
        else if (year == d.year && month < d.month) return false;
        else if (year == d.year && month == d.month && day < d.day) return false;
        else return true;
    }

    bool operator<(const Date& d) const {
        if (!(*this == d) && !(*this > d)) return true;
        else return false;
    }
};

// Date op overload to use std::cout
std::ostream& operator<<(std::ostream& stream, const Date& d) {
    stream << d.date_string;
    return stream;
}

//===============================================================================================================//
// Credit: https://stackoverflow.com/questions/216823/how-to-trim-a-stdstring

// trim from start (in place)
static inline void TrimStringLeft(std::string &s) {
    s.erase(s.begin(), std::find_if(s.begin(), s.end(), [](unsigned char ch) {
        return !std::isspace(ch);
    }));
}

// trim from end (in place)
static inline void TrimStringRight(std::string &s) {
    s.erase(std::find_if(s.rbegin(), s.rend(), [](unsigned char ch) {
        return !std::isspace(ch);
    }).base(), s.end());
}

// trim from both ends (in place)
static inline std::string TrimString(std::string s) {
    TrimStringLeft(s);
    TrimStringRight(s);
    return s;
}

//===============================================================================================================//

std::string RemoveInBetweenChars(std::string s, char c1, char c2) {
    // check if string has both chars
    if (s.find(c1) != std::string::npos && s.find(c2) != std::string::npos) {
        std::string aux; bool in_between = false;
        for (auto& ch : s) {
            if (ch == c1) in_between = true;
            else if (ch == c2) in_between = false;
            else if (!in_between) aux += ch;
        }
        s = aux;
        s = TrimString(s);
    }
    return s;
}

Date StringToDate(std::string s) {
    return Date(std::stoi(s, nullptr), std::stoi(s.substr(3), nullptr), std::stoi(s.substr(6), nullptr), s.substr(0, 10));
}

// Credit: https://www.geeksforgeeks.org/how-to-split-a-string-in-cc-python-and-java/
std::vector<std::string> SplitString(const std::string& s, std::string del = "") {
    int start = 0;
    int end = s.find(del);
    std::vector<std::string> vec;
    while (end != -1) {
        vec.push_back(s.substr(start, end - start));
        start = end + del.size();
        end = s.find(del, start);
    }
    vec.push_back(s.substr(start, end - start));
    return vec;
}

int StringToMin(std::string s) {
    int result = 0;
    std::vector<std::string> aux = SplitString(s, " ");
    if (aux.size() > 1) {
        result += 60 * std::stoi(aux[0].substr(0, (aux[0].size() - 1)));
        result += std::stoi(aux[1].substr(0, (aux[1].size() - 1)));
    } else result += std::stoi(aux[0].substr(0, (aux[0].size() - 1)));
    return result;
}

std::string RemoveDataName(std::string s, std::string data_name = "") {
    return s.substr(data_name.size());
}

float MoneyToFloat(std::string s) {
    std::string aux = "";
    float f;
    for (int i = 0; i < s.length(); i++)
        if (s[i] != '$' && s[i] != ',') aux += s[i];
    try {
        f = std::stof(aux);
    } catch (std::invalid_argument) {
        f = 0.0f;
    }
    return f;
}

std::vector<std::string> ReadKeywords(std::ifstream& fin) {
    std::vector<std::string> keywords;
    std::string s;
    std::getline(fin, s); std::getline(fin, s);
    if (s.find("Nenhuma palavra-chave foi adicionada.") == std::string::npos) {
        for (int i = 0; i < 2; i++) std::getline(fin, s);
        while (s.find("</ul>") == std::string::npos) {
            if (s.find("<li>") != std::string::npos) {
                std::string aux = RemoveInBetweenChars(s, '<', '>');
                if (aux != "")  keywords.push_back(aux);
            }
            std::getline(fin, s);
        }
    }
    return keywords;
}

class Movie {
private:
    std::string m_name;
    std::string m_original_title;
    Date m_release_date;
    int m_runtime;
    std::string m_genre;
    std::string m_original_language;
    std::string m_status;
    float m_budget;
    std::vector<std::string> m_keywords;

public:
    Movie()
        :m_runtime(0), m_budget(0.0f) {}

    Movie(const std::string& name, const std::string& title, const Date& date, const int& runtime, const std::string& genre,
    const std::string& language, const std::string& status, const float& budget, const std::vector<std::string>& keywords)
        :m_name(name), m_original_title(title), m_release_date(date), m_runtime(runtime), m_genre(genre), m_original_language(language),
        m_status(status), m_budget(budget), m_keywords(keywords) {}

    std::string get_name() const {
        return this->m_name;
    }

    std::string get_original_title() const {
        return this->m_original_title;
    }

    Date get_release_date() const {
        return this->m_release_date;
    }

    int get_runtime() const {
        return this->m_runtime;
    }

    std::string get_genre() const {
        return this->m_genre;
    }

    std::string get_original_language() const {
        return this->m_original_language;
    }

    std::string get_status() const {
        return this->m_status;
    }

    float get_budget() const {
        return this->m_budget;
    }

    std::vector<std::string> get_keywords() const {
        return this->m_keywords;
    }

    void set_name(const std::string& name) {
        this->m_name = name;
    }

    void set_original_title(const std::string& title) {
        this->m_original_title = title;
    }

    void set_release_date(const Date& date) {
        this->m_release_date = date;
    }

    void set_runtime(const int& runtime) {
        this->m_runtime = runtime;
    }

    void set_genre(const std::string& genre) {
        this->m_genre = genre;
    }

    void set_original_language(const std::string& language) {
        this->m_original_language = language;
    }

    void set_status(const std::string& status) {
        this->m_status = status;
    }

    void set_budget(const float& budget) {
        this->m_budget = budget;
    }

    void set_keywords(const std::vector<std::string>& keywords) {
        this->m_keywords = keywords;
    }

    static Movie ReadMovie(const std::string& filename) {
        std::ifstream fin;
        fin.open(FILEPATH_PREFIX + filename);
        if (!fin)
            fin.open(FILEPATH_PREFIX_2 + filename);
        std::string s;
        Movie m;
        while (std::getline(fin, s)) {
            if(s.find("h2 class") != std::string::npos) {
                std::getline(fin, s);
                m.set_name(RemoveInBetweenChars(s, '<', '>'));
                m.set_original_title(m.get_name());
            } else if (s.find("\"release\"") != std::string::npos) {
                std::getline(fin, s);
                m.set_release_date(StringToDate(TrimString(s)));
            } else if (s.find("\"genres\"") != std::string::npos) {
                std::getline(fin, s); std::getline(fin, s);
                m.set_genre(RemoveInBetweenChars(RemoveInBetweenChars(s, '<', '>'), '&', ';'));
            } else if (s.find("\"runtime\"") != std::string::npos) {
                std::getline(fin, s); std::getline(fin, s);
                m.set_runtime(StringToMin(TrimString(s)));
            } else if (s.find("Título original") != std::string::npos) {
                m.set_original_title(TrimString(RemoveDataName(RemoveInBetweenChars(s, '<', '>'), "Título original")));
            } else if (s.find("Situação") != std::string::npos) {
                m.set_status(TrimString(RemoveDataName(RemoveInBetweenChars(s, '<', '>'), "Situação")));
            } else if (s.find("Idioma original") != std::string::npos) {
                m.set_original_language(TrimString(RemoveDataName(RemoveInBetweenChars(s, '<', '>'), "Idioma original")));
            } else if (s.find("Orçamento") != std::string::npos) {
                m.set_budget(MoneyToFloat(TrimString(RemoveDataName(RemoveInBetweenChars(s, '<', '>'), "Orçamento"))));
            } else if (s.find("Palavras-chave") != std::string::npos) {
                m.set_keywords(ReadKeywords(fin));
            }
        }
        return m;
    }

    bool operator>(const std::string& original_title) const {
        return this->get_original_title().compare(original_title) > 0;
    }

    bool operator<(const std::string& original_title) const {
        return this->get_original_title().compare(original_title) < 0;
    }

    bool operator==(const std::string& original_title) const {
        return this->get_original_title().compare(original_title) == 0;
    }

    bool operator!=(const std::string& original_title) const {
        return this->get_original_title().compare(original_title) != 0;
    }

    // Movie operator overload to compare with nullptr

    bool operator==(std::nullptr_t null) const {
        Movie m;
        return *this == m.get_original_title();
    }

    bool operator!=(std::nullptr_t null) const {
        Movie m;
        return *this != m.get_original_title();
    }
    
    // Movie operator overload to compare with original title first letter

    bool operator>(const char& key) const {
        return this->get_original_title()[0] > key;
    }

    bool operator<(const char& key) const {
        return this->get_original_title()[0] < key;
    }

    bool operator==(const char& key) const {
        return this->get_original_title()[0] == key;
    }
};

// std::vector<std::string> op overload to use std::cout
std::ostream& operator<<(std::ostream& stream, const std::vector<std::string>& keywords) {
    if (!keywords.empty()) {
        stream << "[" ;
        for (auto it = keywords.begin(); it != std::prev(keywords.end()); ++it)
            stream << *it << ", ";
        stream << keywords.back() << "]";
    } else stream << "[]";
    return stream;
}

// Movie op overload to use std::cout
std::ostream& operator<<(std::ostream& stream, const Movie& m) {
    stream << m.get_name() << " " << m.get_original_title() << " " << m.get_release_date() << " " << m.get_runtime() << " " << m.get_genre() << " "
    << m.get_original_language() << " " << m.get_status() << " " << m.get_budget() << " " << m.get_keywords();
    return stream;
}

template <typename T>
class HashTable {
private:
    T table[30];
    int size, m, n, reserve;
public:
    HashTable()
        :size(30), m(21), n(9), reserve(0) {}

    int ToHash(const std::string& s) const {
        int result = 0;
        for (const auto& it : s) {
            result += it;
        }
        return result % m;
    }

    bool Insert(T data, const std::string& data_string) {
        bool res = false;
        if (data != nullptr) {
            int pos = ToHash(data_string);
            if (table[pos] == nullptr) {
                table[pos] = data;
                res = true;
            } else if (reserve < n) {
                table[m + reserve++] = data;
                res = true;
            }
        }
        return res;
    }

    void Search(const std::string& data_string) const {
        bool res = false;
        int pos = ToHash(data_string);
        if (table[pos] == data_string) {
            res = true;
        } else if (table[pos] != nullptr) {
            for (int i = 0; i < reserve; i++) {
                if (table[m + i] == data_string) {
                    res = true;
                    pos = m + i;
                    i = reserve; // break;
                }
            }
        }
        std::cout << "=> " << data_string << "\n";
        res ? std::cout << "Posicao: " << pos << "\n" : std::cout << "NAO\n";
    }

    void Print() const {
        for (int i = 0; i < m + reserve; i++)
            std::cout << table[i] << "\n";
    }
};

int main() {
    std::string s;
    HashTable<Movie> hash_table;
    std::getline(std::cin, s);
    while(s.compare("FIM") != 0) {
        Movie m = Movie::ReadMovie(s);
        hash_table.Insert(m, m.get_original_title());
        std::getline(std::cin, s);
    }
    std::getline(std::cin, s);
    while(s.compare("FIM") != 0) {
        hash_table.Search(s);
        std::getline(std::cin, s);
    }
}