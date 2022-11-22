#include <iostream>
#include <fstream>
#include <chrono>
#include <vector>
#include <string>
#include <algorithm>
#include <stdexcept>
#include <random>
#include <functional>

#define FILEPATH_PREFIX "tmp/filmes/"
#define FILEPATH_PREFIX_2 "/tmp/filmes/"

//================================================= Movie Class =================================================//

struct Date {
    int day;
    int month;
    int year;
    std::string date_string;

    Date() 
        :day(0), month(0), year(0) {}

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
static inline void TrimStringLeft(std::string& s) {
    s.erase(s.begin(), std::find_if(s.begin(), s.end(), [](unsigned char ch) {
        return !std::isspace(ch);
        }));
}

// trim from end (in place)
static inline void TrimStringRight(std::string& s) {
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
    size_t start = 0;
    size_t end = s.find(del);
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
    }
    else result += std::stoi(aux[0].substr(0, (aux[0].size() - 1)));
    return result;
}

std::string RemoveDataName(std::string s, std::string data_name = "") {
    return s.substr(data_name.size());
}

float MoneyToFloat(std::string s) {
    std::string aux = "";
    float f;
    for (size_t i = 0; i < s.size(); i++)
        if (s[i] != '$' && s[i] != ',') aux += s[i];
    try {
        f = std::stof(aux);
    }
    catch (std::invalid_argument const&) {
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
                if (aux.compare("") != 0)  keywords.push_back(aux);
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
            if (s.find("h2 class") != std::string::npos) {
                std::getline(fin, s);
                m.set_name(RemoveInBetweenChars(s, '<', '>'));
                m.set_original_title(m.get_name());
            }
            else if (s.find("\"release\"") != std::string::npos) {
                std::getline(fin, s);
                m.set_release_date(StringToDate(TrimString(s)));
            }
            else if (s.find("\"genres\"") != std::string::npos) {
                std::getline(fin, s); std::getline(fin, s);
                m.set_genre(RemoveInBetweenChars(RemoveInBetweenChars(s, '<', '>'), '&', ';'));
            }
            else if (s.find("\"runtime\"") != std::string::npos) {
                std::getline(fin, s); std::getline(fin, s);
                m.set_runtime(StringToMin(TrimString(s)));
            }
            else if (s.find("Título original") != std::string::npos) {
                m.set_original_title(TrimString(RemoveDataName(RemoveInBetweenChars(s, '<', '>'), "Título original")));
            }
            else if (s.find("Situação") != std::string::npos) {
                m.set_status(TrimString(RemoveDataName(RemoveInBetweenChars(s, '<', '>'), "Situação")));
            }
            else if (s.find("Idioma original") != std::string::npos) {
                m.set_original_language(TrimString(RemoveDataName(RemoveInBetweenChars(s, '<', '>'), "Idioma original")));
            }
            else if (s.find("Orçamento") != std::string::npos) {
                m.set_budget(MoneyToFloat(TrimString(RemoveDataName(RemoveInBetweenChars(s, '<', '>'), "Orçamento"))));
            }
            else if (s.find("Palavras-chave") != std::string::npos) {
                m.set_keywords(ReadKeywords(fin));
            }
        }
        return m;
    }

    // Movie operator overload to compare with other movie using the original title attribute

    bool operator>(const Movie& m) const {
        return this->get_original_title().compare(m.get_original_title()) > 0;
    }

    bool operator<(const Movie& m) const {
        return this->get_original_title().compare(m.get_original_title()) < 0;
    }

    bool operator<=(const Movie& m) const {
        return this->get_original_title().compare(m.get_original_title()) <= 0;
    }

    bool operator>=(const Movie& m) const {
        return this->get_original_title().compare(m.get_original_title()) >= 0;
    }

    bool operator==(const Movie& m) const {
        return this->get_original_title().compare(m.get_original_title()) == 0;
    }

    // Movie operator overload to compare with original title as a string

    bool operator>(const std::string& original_title) const {
        return this->get_original_title().compare(original_title) > 0;
    }

    bool operator<(const std::string& original_title) const {
        return this->get_original_title().compare(original_title) < 0;
    }

    bool operator==(const std::string& original_title) const {
        return this->get_original_title().compare(original_title) == 0;
    }
};

// std::vector<std::string> op overload to use std::cout
std::ostream& operator<<(std::ostream& stream, const std::vector<std::string>& keywords) {
    if (!keywords.empty()) {
        stream << "[";
        for (auto it = keywords.begin(); it != std::prev(keywords.end()); ++it)
            stream << *it << ", ";
        stream << keywords.back() << "]";
    }
    else stream << "[]";
    return stream;
}

// Movie op overload to use std::cout
std::ostream& operator<<(std::ostream& stream, const Movie& m) {
    stream << m.get_name() << " " << m.get_original_title() << " " << m.get_release_date() << " " << m.get_runtime() << " " << m.get_genre() << " "
        << m.get_original_language() << " " << m.get_status() << " " << m.get_budget() << " " << m.get_keywords();
    return stream;
}

//============================================= AVL Tree ==============================================//

template <typename T>
class AvlTree {
    struct Node {
        T data;
        Node* left;
        Node* right;
        int level;

        Node()
            :data(), level(1) {}
        Node(T data)
            :data(data), level(1) {
            this->left = nullptr;
            this->right = nullptr;
        }

        void set_level() {
            this->level = 1 + std::max(get_level(this->left), get_level(this->right));
        }

        static int get_level(const Node* node) {
            return (node == nullptr) ? 0 : node->level;
        }
    };

private:
    Node* m_root;
    void PrintPreOrder(Node* node);
    void PrintInOrder(Node* node);
    void PrintPostOrder(Node* node);
    bool Insert(Node** node, T data);
    AvlTree::Node* Remove(Node* node, T data);
    bool Search(Node* node, std::string& key);
    void RemoveByKey(Node* node, std::string& key);
    void DeleteTree(Node* node);
    int Size(Node* node);
    AvlTree::Node* Balance(Node* node);
    AvlTree::Node* RotateRight(Node* node);
    AvlTree::Node* RotateLeft(Node* node);

public:
    long m_num_comp;

    AvlTree()
        :m_root(), m_num_comp(0) {}

    ~AvlTree() {
        DeleteTree(this->m_root);
    }

    void PrintPreOrder() {
        PrintPreOrder(this->m_root);
        std::cout << "\n";
    }

    void PrintInOrder() {
        PrintInOrder(this->m_root);
        std::cout << "\n";
    }

    void PrintPostOrder() {
        PrintPostOrder(this->m_root);
        std::cout << "\n";
    }

    int Size() {
        return Size(this->m_root) - 1;
    }

    bool Insert(T data);
    void Remove(T data);
    void RemoveByKey(std::string& key);
    bool Search(std::string& key);
    void SearchDo(std::string& key, std::function<void(T &data)>);
};

template <typename T>
void AvlTree<T>::PrintPreOrder(Node* node) {
    if (node != nullptr) {
        std::cout << node->data << ", ";
        PrintPreOrder(node->left);
        PrintPreOrder(node->right);
    }
}

template <typename T>
void AvlTree<T>::PrintInOrder(Node* node) {
    if (node != nullptr) {
        PrintInOrder(node->left);
        std::cout << node->data << "\n";
        PrintInOrder(node->right);
    }
}

template <typename T>
void AvlTree<T>::PrintPostOrder(Node* node) {
    if (node != nullptr) {
        PrintPostOrder(node->left);
        PrintPostOrder(node->right);
        std::cout << node->data << ", ";
    }
}

template <typename T>
int AvlTree<T>::Size(Node* node) {
    int size = 1;
    if (node != nullptr) {
        int left_size = Size(node->left), right_size = Size(node->right);
        left_size > right_size ? size += left_size : size += right_size;
    }
    return size;
}

template <typename T>
void AvlTree<T>::DeleteTree(Node* node) {
    if (node != nullptr) {
        DeleteTree(node->left);
        DeleteTree(node->right);
        delete node;
    }
}

template <typename T>
bool AvlTree<T>::Insert(T data) {
    return Insert(&(this->m_root), data);
}

template <typename T>
bool AvlTree<T>::Insert(Node** node, T data) {
    bool res = true;
    if (*node == nullptr) {
        *node = new Node(data);
    }
    else if (data > (*node)->data) {
        Insert(&(*node)->right, data);
    }
    else if (data < (*node)->data) {
        Insert(&(*node)->left, data);
    }
    else if (data == (*node)->data) {
        res = false;
    }
    *node = Balance(*node);
    return res;
}

template <typename T>
void AvlTree<T>::Remove(T data) {
    this->m_root = Remove(this->m_root, data);
}

template <typename T>
void AvlTree<T>::RemoveByKey(std::string& key) {
    RemoveByKey(this->m_root, key);
}

template <typename T>
typename AvlTree<T>::Node* AvlTree<T>::Remove(Node* node, T data) {
    if (data < node->data)
        node->left = Remove(node->left, data);
    else if (data > node->data)
        node->right = Remove(node->right, data);
    else if (data == node->data) {
        if (!node->left) {
            Node* temp = node->right;
            node = nullptr;
            return temp;
        }
        else if (!node->right) {
            Node* temp = node->left;
            node = nullptr;
            return temp;
        }
        Node* temp = node->left;
        while (temp->right) temp = temp->right;
        node->data = temp->data;
        node->left = Remove(node->left, node->data);
    }
    return Balance(node);
}

template <typename T>
bool AvlTree<T>::Search(std::string& key) {
    std::cout << "raiz ";
    return Search(this->m_root, key);
}

template <typename T>
bool AvlTree<T>::Search(Node* node, std::string& key) {
    if (node == nullptr) {
        return false;
    }
    else {
        if (node->data == key) return true;
        else if (node->data < key) {
            std::cout << "dir ";
            return Search(node->right, key);
        }
        else {
            std::cout << "esq ";
            return Search(node->left, key);
        }
    }
}

template <typename T>
void AvlTree<T>::RemoveByKey(Node* node, std::string& key) {
    if (node == nullptr) {
        return;
    } else {
        if (node->data == key) 
            this->m_root = Remove(this->m_root, node->data);
        else if (node->data < key) {
            RemoveByKey(node->right, key);
        }
        else {
            RemoveByKey(node->left, key);
        }
    }
}

template<typename T>
typename AvlTree<T>::Node* AvlTree<T>::Balance(Node* node) {
    if (node != nullptr) {
        int factor = Node::get_level(node->right) - Node::get_level(node->left);
        if (std::abs(factor) <= 1) {
            node->set_level();
        }
        else if (factor == 2) {
            if ((Node::get_level(node->right->right) - Node::get_level(node->right->left)) == -1) {
                node->right = RotateRight(node->right);
            }
            node = RotateLeft(node);
        }
        else if (factor == -2) {
            if ((Node::get_level(node->left->right) - Node::get_level(node->left->left)) == 1) {
                node->left = RotateLeft(node->left);
            }
            node = RotateRight(node);
        }
        else throw std::runtime_error("Balance error");
    }
    return node;
}

template<typename T>
typename AvlTree<T>::Node* AvlTree<T>::RotateRight(Node* node) {
    Node *left_node = node->left;
    Node *left_right_node = left_node->right;
    left_node->right = node;
    node->left = left_right_node;
    node->set_level();
    left_node->set_level();
    return left_node;
}

template<typename T>
typename AvlTree<T>::Node* AvlTree<T>::RotateLeft(Node* node) {
    Node *right_node = node->right;
    Node *right_left_node = right_node->left;
    right_node->left = node;
    node->right = right_left_node;
    node->set_level();
    right_node->set_level();
    return right_node;
}

//================================================= Driver code =================================================//

int main() {
    AvlTree<Movie> avl_tree;
    std::string s;
    std::getline(std::cin, s);
    while (s.compare("FIM") != 0) {
        avl_tree.Insert(Movie::ReadMovie(s));
        std::getline(std::cin, s);
    }
    int count = 0;
    std::getline(std::cin, s);
    count = stoi(s);
    for (int i = 0; i < count; i++) {
        std::getline(std::cin, s);
        if (s.find("I ") != std::string::npos) {
            s = s.substr(2);
            avl_tree.Insert(Movie::ReadMovie(s));
        }
        else {
            s = s.substr(2);
            avl_tree.RemoveByKey(s);
        }
    }
    std::getline(std::cin, s);
    while (s.compare("FIM") != 0) {
        std::cout << s << "\n";
        avl_tree.Search(s) ? std::cout << "SIM\n" : std::cout << "NAO\n";
        std::getline(std::cin, s);
    }
}