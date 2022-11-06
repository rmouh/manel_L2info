#ifndef HE_H
# define HE_H

# include <unistd.h>
# include <stdlib.h>
# ifndef BUFFER_SIZE
# define BUFFER_SIZE 6
# endif

char	*get_next_line(int fd);

size_t	ft_strlen(char *s);
char	*ft_strchr(char *s, char c);
char	*ft_strjoin(char *s1, char *s2);

#endif