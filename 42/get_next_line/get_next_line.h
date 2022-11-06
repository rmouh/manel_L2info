#ifndef GET_NEXT_LINE_H
# define GET_NEXT_LINE_H

# include <stdlib.h>
# include <unistd.h>
# ifndef OPEN_MAX
# define OPEN_MAX 256
# endif

# ifndef BUFFER_SIZE
# define BUFFER_SIZE 6
# endif

int         reached_EOF(char *str);
size_t      ft_strlen(const char *s);
char        *ft_strdup(const char *s);
char        *get_next_line(int fd);
char        *ft_strjoin(char *s1, char *s2);
void        *ft_memcpy(void *dest, const void *src, size_t n);


#endif